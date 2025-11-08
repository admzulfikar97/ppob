package com.adamzulfikar.ppob.transaction.dto.request;

import jakarta.validation.constraints.NotBlank;

public class TransactionRequest {
    @NotBlank
    public String service_code;
}
