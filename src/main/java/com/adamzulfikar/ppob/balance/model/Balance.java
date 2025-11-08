package com.adamzulfikar.ppob.balance.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Balance {
    private Long id;
    private Long user_id;
    private Long balance;
    private String updatedAt;

    public Balance() {}

    public Balance(Long id, Long user_id, Long balance, String updatedAt) {
        this.id = id;
        this.user_id = user_id;
        this.balance = balance;
        this.updatedAt = updatedAt;
    }
    public Balance(Long balance) {
        this.balance = balance;
    }
}
