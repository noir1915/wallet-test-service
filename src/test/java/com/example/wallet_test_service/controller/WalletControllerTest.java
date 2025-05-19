package com.example.wallet_test_service.controller;

import com.example.wallet_test_service.dto.WalletOperationRequest;
import com.example.wallet_test_service.exception.WalletNotFoundException;
import com.example.wallet_test_service.service.WalletService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Import(WalletTestConfig.class)
public class WalletControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WalletService walletService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testPerformOperation_Success() throws Exception {
        WalletOperationRequest request = new WalletOperationRequest(/* параметры */);

        doNothing().when(walletService).performOperation(any(), any(), any());

        mockMvc.perform(post("/api/v1/wallets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    public void testPerformOperation_WalletNotFound() throws Exception {
        WalletOperationRequest request = new WalletOperationRequest();

        doThrow(new WalletNotFoundException("Кошелёк не найден")).when(walletService).performOperation(any(), any(), any());

        mockMvc.perform(post("/api/v1/wallets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Кошелёк не найден"));
    }

    @Test
    public void testGetBalance_Success() throws Exception {
        UUID walletId = UUID.randomUUID();
        Long balance = 1000L;

        when(walletService.getBalance(walletId)).thenReturn(balance);

        mockMvc.perform(get("/api/v1/wallets/" + walletId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.walletId").value(walletId.toString()))
                .andExpect(jsonPath("$.balance").value(balance));
    }

    @Test
    public void testGetBalance_WalletNotFound() throws Exception {
        UUID walletId = UUID.randomUUID();

        when(walletService.getBalance(walletId)).thenThrow(new WalletNotFoundException("Кошелёк с UUID: " + walletId + " не найден"));

        mockMvc.perform(get("/api/v1/wallets/" + walletId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Кошелёк с UUID: " + walletId + " не найден"));
    }

}
