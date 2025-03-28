package ru.example.simplewebwallet.wallet;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.example.simplewebwallet.wallet.dto.WalletDto;
import ru.example.simplewebwallet.wallet.dto.WalletShortDto;
import ru.example.simplewebwallet.wallet.model.Wallet;
import ru.example.simplewebwallet.wallet.service.WalletService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/wallet")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WalletController {
    WalletService walletService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Wallet createWallet() {
        return walletService.createWallet();
    }

    @PatchMapping
    public WalletShortDto addOrWithdrawMoney(@RequestBody WalletDto walletDto) {
        return walletService.addOrWithdrawMoney(walletDto);
    }

    @GetMapping("/{WALLET_UUID}")
    public WalletShortDto getWalletData(@PathVariable("WALLET_UUID") UUID walletId) {
        return walletService.getWalletData(walletId);
    }
}
