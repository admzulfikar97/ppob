package com.adamzulfikar.ppob.transaction.controller;

import com.adamzulfikar.ppob.common.dto.ApiResponse;
import com.adamzulfikar.ppob.transaction.dto.request.TransactionRequest;
import com.adamzulfikar.ppob.transaction.model.Transaction;
import com.adamzulfikar.ppob.transaction.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
    @GetMapping("transaction/history")
    public ResponseEntity<?> listTransaction(Authentication authentication, @RequestParam(defaultValue = "0") int offset,
                                             @RequestParam(defaultValue = "5") int limit) throws Exception {
        String email = authentication.getPrincipal().toString();
        List<Transaction> transactionList = transactionService.getListTransaction(email, limit, offset);
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("offset", offset);
        data.put("limit", limit);
        data.put("records", transactionList);

        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Get History Berhasil", data));
    }
}