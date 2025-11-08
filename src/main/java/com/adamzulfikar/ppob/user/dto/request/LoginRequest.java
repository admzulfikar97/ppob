package com.adamzulfikar.ppob.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class LoginRequest {
    @Email
    @NotBlank
    public String email;
    @NotBlank
    @Size(min = 8, message = "Password minimal 8 karakter")
    public String password;
}
