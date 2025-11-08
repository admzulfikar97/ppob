package com.adamzulfikar.ppob.transaction.controller;

import com.adamzulfikar.ppob.common.dto.ApiResponse;
import com.adamzulfikar.ppob.transaction.dto.request.TransactionRequest;
import com.adamzulfikar.ppob.transaction.model.Transaction;
import com.adamzulfikar.ppob.transaction.service.TransactionService;
import com.adamzulfikar.ppob.user.dto.request.LoginRequest;
import com.adamzulfikar.ppob.user.dto.request.RegisterRequest;
import com.adamzulfikar.ppob.user.dto.request.UserRequest;
import com.adamzulfikar.ppob.user.dto.response.LoginResponse;
import com.adamzulfikar.ppob.user.dto.response.RegisterResponse;
import com.adamzulfikar.ppob.user.model.User;
import com.adamzulfikar.ppob.user.service.ImageFileService;
import com.adamzulfikar.ppob.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;
    @PostMapping("transaction")
    public ResponseEntity<?> transaction(Authentication authentication, @Valid @RequestBody TransactionRequest req) {
        String email = authentication.getPrincipal().toString();
        Transaction transaction = transactionService.createTransaction(req.service_code, email);

        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Transaksi Berhasil", transaction));
    }
}