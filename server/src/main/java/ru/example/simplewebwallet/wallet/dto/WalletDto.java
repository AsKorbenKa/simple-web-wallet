package ru.example.simplewebwallet.wallet.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.example.simplewebwallet.wallet.enums.OperationType;

import java.util.UUID;

@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WalletDto {
    UUID walletId;
    OperationType operationType;
    Long amount;
}
