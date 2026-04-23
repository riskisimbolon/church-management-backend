package com.example.gmahk.service;


import com.example.gmahk.dto.response.UserResponse;

import java.util.List;

public interface UserService {
    List<UserResponse> findAll();
    UserResponse findById(Integer id);
    UserResponse findByEmail(String email);
    void deleteById(Integer id);
    void updatePassword(Integer id, String newPassword);
    void changePassword(String email, String oldPassword, String newPassword);
}
