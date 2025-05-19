package com.example.wallet_test_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@AllArgsConstructor
@Data
public class WalletBalanceResponse {
    private UUID walletId;
    private Long balance;
}
