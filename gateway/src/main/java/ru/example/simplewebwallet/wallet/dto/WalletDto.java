package ru.example.simplewebwallet.wallet.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
    @NotNull(message = "Id кошелька должен быть указан.")
    UUID walletId;

    OperationType operationType;

    @Positive(message = "Сумма денежных средств не может быть меньше или равна 0.")
    Long amount;

}
