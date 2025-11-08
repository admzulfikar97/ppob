package com.adamzulfikar.ppob.user.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegisterResponse {
    private Long id;
    private String email;
    private String first_name;
    private String last_name;
    private String password;
    private String filename;
    private String profile_image;
    private String createdAt;
}
