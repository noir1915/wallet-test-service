package com.example.wallet_test_service.controller;

import com.example.wallet_test_service.dto.*;
import com.example.wallet_test_service.service.WalletService;
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
    public ResponseEntity<?> performOperation(@RequestBody WalletOperationRequest request) {
        walletService.performOperation(request.getWalletId(), request.getOperationType(), request.getAmount());
        log.info("Операция успешно завершена");
        return ResponseEntity.ok("Операция успешно завершена\n" +
                "Текущий баланс после операции: " + walletService.getBalance(request.getWalletId()));
    }

    @GetMapping("/{walletId}")
    public ResponseEntity<?> getBalance(@PathVariable UUID walletId) {
        return ResponseEntity.ok("UUID: " + walletId + "\n" +
                "Текущий баланс: " + walletService.getBalance(walletId));
    }
}