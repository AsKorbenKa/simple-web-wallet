package ru.example.simplewebwallet.wallet;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.example.simplewebwallet.wallet.dto.WalletDto;

import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/wallet")
@RequiredArgsConstructor
@Slf4j
public class WalletController {
    private final WalletClient walletClient;

    @PostMapping
    public ResponseEntity<Object> addOrWithdrawMoney(@Valid @RequestBody WalletDto walletDto) {
        log.debug("Обновляем данные кошелька {}", walletDto);
        return walletClient.addOrWithdrawMoney(walletDto);
    }

    @GetMapping("/{WALLET_UUID}")
    public ResponseEntity<Object> getWalletData(@PathVariable("WALLET_UUID") UUID walletId) {
        log.debug("Получаем данные кошелька по его id: {}", walletId);
        return walletClient.getWalletData(walletId);
    }
}
