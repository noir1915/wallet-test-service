package com.example.wallet_test_service.controller;

import com.example.wallet_test_service.dto.*;
import com.example.wallet_test_service.service.WalletService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/wallets")
@RequiredArgsConstructor
@Slf4j
public class WalletController {

    private final WalletService walletService;

    @PostMapping
    public ResponseEntity<?> performOperation(@Valid @RequestBody WalletOperationRequest request) {
        log.info("Запрос на операцию: {}", request);
        walletService.performOperation(request.getWalletId(), request.getOperationType(), request.getAmount());
        Long balance = walletService.getBalance(request.getWalletId());
        log.info("Операция {} успешно завершена для кошелька {}. Текущий баланс: {}",
                request.getOperationType(), request.getWalletId(), balance);
        return ResponseEntity.ok(new ApiResponse("Операция успешно завершена", balance));
    }

    @GetMapping("/{walletId}")
    public ResponseEntity<?> getBalance(@PathVariable UUID walletId) {
        Long balance = walletService.getBalance(walletId);
        return ResponseEntity.ok(new WalletBalanceResponse(walletId, balance));
    }
}