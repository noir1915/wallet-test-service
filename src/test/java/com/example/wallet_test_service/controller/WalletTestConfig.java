package com.example.wallet_test_service.controller;

import com.example.wallet_test_service.service.WalletService;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class WalletTestConfig {

    @Bean
    public WalletService walletService() {
        return Mockito.mock(WalletService.class);
    }
}
