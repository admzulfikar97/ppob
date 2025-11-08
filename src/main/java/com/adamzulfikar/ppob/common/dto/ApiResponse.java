package com.adamzulfikar.ppob.common.dto;

public record ApiResponse<T>(Integer status, String message, T data) {
}
