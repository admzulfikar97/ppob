package com.adamzulfikar.ppob.user.dto.request;

import jakarta.validation.constraints.NotBlank;

public class UserRequest {
    @NotBlank
    public String first_name;
    @NotBlank
    public String last_name;
}
