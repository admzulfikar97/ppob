package com.adamzulfikar.ppob.information.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Banner {
    private Long id;
    private String banner_name;
    private String banner_image;
    private String description;
    private String createdAt;

    public Banner() {}

    public Banner(Long id, String banner_name, String banner_image, String description, String createdAt) {
        this.id = id;
        this.banner_name = banner_name;
        this.banner_image = banner_image;
        this.description = description;
        this.createdAt = createdAt;
    }
    public Banner(String banner_name, String banner_image, String description) {
        this.banner_name = banner_name;
        this.banner_image = banner_image;
        this.description = description;
    }}
