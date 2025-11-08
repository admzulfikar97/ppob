package com.adamzulfikar.ppob.balance.controller;

import com.adamzulfikar.ppob.balance.dto.request.TopUpRequest;
import com.adamzulfikar.ppob.balance.model.Balance;
import com.adamzulfikar.ppob.balance.service.BalanceService;
import com.adamzulfikar.ppob.common.dto.ApiResponse;
import com.adamzulfikar.ppob.user.dto.request.UserRequest;
import com.adamzulfikar.ppob.user.dto.response.RegisterResponse;
import com.adamzulfikar.ppob.user.model.User;
import com.adamzulfikar.ppob.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class BalanceController {
    @Autowired
    private UserService userService;
    @Autowired
    private BalanceService balanceService;
    @GetMapping("balance")
    public ResponseEntity<?> getUser(Authentication authentication) throws Exception {
        String email = authentication.getPrincipal().toString();
        Balance balance = balanceService.getBalanceByEmail(email);
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Get Balance Berhasil", balance));
    }
    @PostMapping("topup")
    public ResponseEntity<?> topupBalance(Authentication authentication, @Valid @RequestBody TopUpRequest topUpRequest) throws Exception {
        String email = authentication.getPrincipal().toString();
        Balance balance = balanceService.updateBalance(topUpRequest, email);

        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Top Up Balance berhasil", balance));
    }
}
