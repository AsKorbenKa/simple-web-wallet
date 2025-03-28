package ru.example.simplewebwallet.wallet.service;

import ru.example.simplewebwallet.wallet.dto.WalletDto;
import ru.example.simplewebwallet.wallet.dto.WalletShortDto;
import ru.example.simplewebwallet.wallet.model.Wallet;

import java.util.UUID;

public interface WalletService {
    Wallet createWallet();

    WalletShortDto addOrWithdrawMoney(WalletDto walletDto);

    WalletShortDto getWalletData(UUID walletId);
}
