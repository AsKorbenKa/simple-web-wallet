package ru.example.simplewebwallet.wallet.service;

import ru.example.simplewebwallet.wallet.dto.WalletDto;
import ru.example.simplewebwallet.wallet.dto.WalletShortDto;

import java.util.UUID;

public interface WalletService {
    WalletShortDto addOrWithdrawMoney(WalletDto walletDto);

    WalletShortDto getWalletData(UUID walletId);
}
