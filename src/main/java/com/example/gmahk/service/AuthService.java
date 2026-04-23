package com.example.gmahk.service;


import com.example.gmahk.dto.response.AuthResponse;
import com.example.gmahk.dto.request.LoginRequest;
import com.example.gmahk.dto.request.RegisterRequest;
import com.example.gmahk.dto.response.UserResponse;

public interface AuthService {
    AuthResponse login(LoginRequest request);
    UserResponse register(RegisterRequest request);
}
