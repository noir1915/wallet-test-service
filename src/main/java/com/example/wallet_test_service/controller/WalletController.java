package com.example.wallet_test_service.controller;

import com.example.wallet_test_service.dto.*;
import com.example.wallet_test_service.exception.InsufficientFundsException;
import com.example.wallet_test_service.exception.InvalidOperationException;
import com.example.wallet_test_service.exception.WalletNotFoundException;
import com.example.wallet_test_service.service.WalletService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
        try {
            walletService.performOperation(request.getWalletId(), request.getOperationType(), request.getAmount());
            log.info("Операция успешно завершена");
            return ResponseEntity.ok().build();
        } catch (WalletNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Кошелёк не найден"));
        } catch (InsufficientFundsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Недостаточно средств"));
        } catch (InvalidOperationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Неверный тип операции"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse("Внутренняя ошибка сервера"));
        }
    }

    @GetMapping("/{walletId}")
    public ResponseEntity<?> getBalance(@PathVariable UUID walletId) {
        try {
            Long balance = walletService.getBalance(walletId);
            return ResponseEntity.ok(new WalletBalanceResponse(walletId, balance));
        } catch (WalletNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Кошелёк с UUID: " + walletId + " не найден"));
        }
    }
}