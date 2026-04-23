package com.example.gmahk.service.impl;

import com.example.gmahk.dto.response.UserResponse;
import com.example.gmahk.entity.User;
import com.example.gmahk.repository.UserRepository;
import com.example.gmahk.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<UserResponse> findAll() {
        return userRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public UserResponse findById(Integer id) {
        return toResponse(userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User tidak ditemukan dengan id: " + id)));
    }

    @Override
    public UserResponse findByEmail(String email) {
        return toResponse(userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User tidak ditemukan dengan email: " + email)));
    }

    @Override
    public void deleteById(Integer id) {
        if (!userRepository.existsById(id))
            throw new RuntimeException("User tidak ditemukan dengan id: " + id);
        userRepository.deleteById(id);
    }

    // Admin reset password user lain — tidak perlu verifikasi password lama
    @Override
    @Transactional
    public void updatePassword(Integer id, String newPassword) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User tidak ditemukan dengan id: " + id));
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    // User ganti password sendiri — wajib verifikasi password lama dulu
    @Override
    @Transactional
    public void changePassword(String email, String oldPassword, String newPassword) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User tidak ditemukan"));

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new RuntimeException("Password lama tidak sesuai");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    private UserResponse toResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .nama(user.getNama())
                .email(user.getEmail())
                .role(user.getRole().getNamaRole())
                .jemaatId(user.getJemaatId())
                .status(user.getStatus().name())
                .build();
    }
}