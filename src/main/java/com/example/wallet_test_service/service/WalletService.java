package com.example.wallet_test_service.service;

import com.example.wallet_test_service.exception.*;
import com.example.wallet_test_service.model.Wallet;
import com.example.wallet_test_service.repository.WalletRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class WalletService {

    private final WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    @Transactional
    public void performOperation(UUID walletId, String operationType, Long amount)
            throws WalletNotFoundException, InsufficientFundsException, InvalidOperationException {

        Optional<Wallet> optionalWallet = walletRepository.findByIdForUpdate(walletId);
        if (optionalWallet.isEmpty()) {
            throw new WalletNotFoundException("Кошелек не найден " + walletId);
        }
        Wallet wallet = optionalWallet.get();

        if ("DEPOSIT".equalsIgnoreCase(operationType)) {
            log.info("Пополнение кошелька на {}", amount);
            wallet.setBalance(wallet.getBalance() + amount);
        } else if ("WITHDRAW".equalsIgnoreCase(operationType)) {
            log.info("Снятие средств в размере {}", amount);
            if (wallet.getBalance() < amount) {
                throw new InsufficientFundsException("Недостаточно средств для снятия " + operationType);
            }
            wallet.setBalance(wallet.getBalance() - amount);
        } else {
            throw new InvalidOperationException("Внутренняя ошибка сервера: " + operationType);
        }

        walletRepository.save(wallet);
    }

    @Transactional(readOnly = true)
    public Long getBalance(UUID walletId) throws WalletNotFoundException {
        Optional<Wallet> optionalWallet = walletRepository.findById(walletId);
        if (optionalWallet.isEmpty()) {
            throw new WalletNotFoundException("Кошелек не с UUID: " + walletId + "не найден ");
        }
        return optionalWallet.get().getBalance();
    }
}
