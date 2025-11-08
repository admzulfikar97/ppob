package com.adamzulfikar.ppob.transaction.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Services {
    private String service_code;
    private String service_name;
    private String service_icon;
    private Long service_tariff;

    public Services() {}

    public Services(String service_code, String service_name, String service_icon, Long service_tariff) {
        this.service_code = service_code;
        this.service_name = service_name;
        this.service_icon = service_icon;
        this.service_tariff = service_tariff;
    }

//    public User(String filename, String filepath) {
//        this.filename = filename;
//        this.filepath = filepath;
//    }
}
