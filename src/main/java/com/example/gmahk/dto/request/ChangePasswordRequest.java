package com.example.gmahk.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePasswordRequest {

    @NotBlank(message = "Password lama tidak boleh kosong")
    private String oldPassword;

    @NotBlank(message = "Password baru tidak boleh kosong")
    @Size(min = 6, message = "Password baru minimal 6 karakter")
    private String newPassword;
}
