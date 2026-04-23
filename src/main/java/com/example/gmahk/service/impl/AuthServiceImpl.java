package com.example.gmahk.service.impl;



import com.example.gmahk.dto.response.AuthResponse;
import com.example.gmahk.dto.request.LoginRequest;
import com.example.gmahk.dto.request.RegisterRequest;
import com.example.gmahk.dto.response.UserResponse;
import com.example.gmahk.security.service.JwtService;
import com.example.gmahk.entity.Role;
import com.example.gmahk.entity.User;
import com.example.gmahk.repository.RoleRepository;
import com.example.gmahk.repository.UserRepository;
import com.example.gmahk.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User tidak ditemukan"));

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        String token = jwtService.generateToken(userDetails);

        return AuthResponse.builder()
                .accessToken(token)
                .tokenType("Bearer")
                .userId(user.getId())
                .nama(user.getNama())
                .email(user.getEmail())
                .role(user.getRole().getNamaRole())
                .build();
    }

    @Override
    @Transactional
    public UserResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email sudah terdaftar: " + request.getEmail());
        }

        Role role = roleRepository.findById(request.getRoleId())
                .orElseThrow(() -> new RuntimeException("Role tidak ditemukan: " + request.getRoleId()));

        User user = User.builder()
                .nama(request.getNama())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .jemaatId(request.getJemaatId())
                .status(User.StatusUser.aktif)
                .build();

        User saved = userRepository.save(user);

        return UserResponse.builder()
                .id(saved.getId())
                .nama(saved.getNama())
                .email(saved.getEmail())
                .role(saved.getRole().getNamaRole())
                .jemaatId(saved.getJemaatId())
                .status(saved.getStatus().name())
                .build();
    }
}
