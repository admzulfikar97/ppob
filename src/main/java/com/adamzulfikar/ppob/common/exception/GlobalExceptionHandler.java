package com.adamzulfikar.ppob.common.exception;

import com.adamzulfikar.ppob.common.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

import java.lang.reflect.InvocationTargetException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidationErrors(MethodArgumentNotValidException ex) {
        // Ambil field error pertama
        String errorMessage = ex.getBindingResult().getFieldErrors()
                .stream()
                .findFirst()
                .map(err -> {
                    // Bisa kustom sesuai field
                    switch (err.getField()) {
                        case "email":
                            if (err.getCode().equals("NotBlank")) {
                                return "Parameter email tidak boleh kosong";
                            } else if (err.getCode().equals("Email")) {
                                return "Parameter email tidak sesuai format";
                            } else {
                                return "Parameter email tidak sesuai format";
                            }
                        case "first_name":
                            return "First name tidak boleh kosong";
                        case "last_name":
                            return "Last name tidak boleh kosong";
                        case "password":
                            if (err.getCode().equals("NotBlank")) {
                                return "Parameter password tidak boleh kosong";
                            } else if (err.getCode().equals("Size")) {
                                return "Parameter password minimal 8 karakter";
                            } else {
                                return "Parameter password tidak sesuai";
                            }
                        case "top_up_amount":
                            if (err.getCode().equals("NotNull")) {
                                return "Parameter top_up_amount tidak boleh kosong";
                            } else if (err.getCode().equals("Positive")) {
                                return "Paramter amount hanya boleh angka dan tidak boleh lebih kecil dari 0";
                            } else {
                                return "Parameter top_up_amount tidak sesuai";
                            }
                        default:
                            return err.getDefaultMessage();
                    }
                })
                .orElse("Parameter tidak valid");

        ApiResponse<Void> response = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), errorMessage, null);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleGeneralError(Exception ex) {
        Throwable root = getRootCause(ex);

        if (root instanceof BadRequestException badReq) {
            return handleBadRequest(badReq);
        }
        if (root instanceof NotFoundException notFound) {
            return handleNotFound(notFound);
        }
        if (root instanceof UnauthorizedException unauthorized) {
            return handleUnauthorized(unauthorized);
        }

        ApiResponse<String> response = new ApiResponse<>(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Server Error: " + root.getMessage(),
                null
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    private Throwable getRootCause(Throwable ex) {
        while (ex instanceof InvocationTargetException || ex.getCause() != null) {
            ex = ex.getCause();
        }
        return ex;
    }

    // 🔹 Handle error khusus (misal validasi / login)
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiResponse<String>> handleBadRequest(BadRequestException ex) {
        ApiResponse<String> response = new ApiResponse<>(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                null
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiResponse<String>> handleNotFound(NotFoundException ex) {
        ApiResponse<String> response = new ApiResponse<>(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                null
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(HttpClientErrorException.Unauthorized.class)
    public ResponseEntity<ApiResponse<String>> handleUnauthorized(UnauthorizedException ex) {
        ApiResponse<String> response = new ApiResponse<>(
                HttpStatus.UNAUTHORIZED.value(),
                ex.getMessage(),
                null
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }
}
