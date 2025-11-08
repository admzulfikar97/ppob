package com.adamzulfikar.ppob.transaction.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Transaction {
    private Long user_id;
    private String invoice_number;
    private String service_code;
    private String service_name;
    private String transaction_type;
    private Long total_amount;
    private String created_at;

    public Transaction() {}

    public Transaction(Long user_id, String invoice_number, String service_code, String service_name, String transaction_type
            , Long total_amount, String created_at) {
        this.user_id = user_id;
        this.invoice_number = invoice_number;
        this.service_code = service_code;
        this.service_name = service_name;
        this.transaction_type = transaction_type;
        this.total_amount = total_amount;
        this.created_at = created_at;
    }
}
