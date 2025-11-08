package com.adamzulfikar.ppob.information.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BannerResponse {
    private String banner_name;
    private String banner_image;
    private String description;
    private String createdAt;
}
