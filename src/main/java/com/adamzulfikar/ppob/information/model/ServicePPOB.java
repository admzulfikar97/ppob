package com.adamzulfikar.ppob.information.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServicePPOB {
    private Long id;
    private String service_code;
    private String service_name;
    private String service_icon;
    private Integer service_tariff;
    private String createdAt;

    public ServicePPOB() {}

    public ServicePPOB(Long id, String service_code, String service_name, String service_icon, Integer service_tariff, String createdAt) {
        this.id = id;
        this.service_code = service_code;
        this.service_name = service_name;
        this.service_icon = service_icon;
        this.service_tariff = service_tariff;
        this.createdAt = createdAt;
    }
    public ServicePPOB(String service_code, String service_name, String service_icon, Integer service_tariff) {
        this.service_code = service_code;
        this.service_name = service_name;
        this.service_icon = service_icon;
        this.service_tariff = service_tariff;
    }
}
