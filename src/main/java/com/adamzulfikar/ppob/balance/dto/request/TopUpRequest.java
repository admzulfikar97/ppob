package com.adamzulfikar.ppob.balance.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class TopUpRequest {
    @NotNull
    @Positive
    public Long top_up_amount;
}
