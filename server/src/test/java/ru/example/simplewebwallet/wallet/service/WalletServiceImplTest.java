package ru.example.simplewebwallet.wallet.service;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.example.simplewebwallet.exception.InsufficientFundsException;
import ru.example.simplewebwallet.exception.NotFoundException;
import ru.example.simplewebwallet.wallet.WalletController;
import ru.example.simplewebwallet.wallet.dto.WalletDto;
import ru.example.simplewebwallet.wallet.dto.WalletShortDto;
import ru.example.simplewebwallet.wallet.enums.OperationType;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class WalletServiceImplTest {
    WalletController walletController;

    @Autowired
    public WalletServiceImplTest(WalletController walletController) {
        this.walletController = walletController;
    }

    @Test
    @DisplayName("Test deposit and withdraw funds.")
    void depositAndWithdrawFundsTest() {
        WalletDto walletDto = new WalletDto(UUID.fromString("22b623ab-2675-45c9-b8fd-c3e0a44f2e04"),
                OperationType.DEPOSIT, 1234L);
        WalletShortDto changedWallet = walletController.addOrWithdrawMoney(walletDto);

        assertEquals(2345L, changedWallet.getAmount());

        WalletDto anotherWalletDto = new WalletDto(UUID.fromString("22b623ab-2675-45c9-b8fd-c3e0a44f2e04"),
                OperationType.WITHDRAW, 100L);
        WalletShortDto changedWallet2 = walletController.addOrWithdrawMoney(anotherWalletDto);

        assertEquals(2245L, changedWallet2.getAmount());
    }

    @Test
    @DisplayName("Test withdraw funds when insufficient funds.")
    void withdrawWhenInsufficientFundsThenTrowExceptionTest() {
        WalletDto walletDto = new WalletDto(UUID.fromString("22b623ab-2675-45c9-b8fd-c3e0a44f2e04"),
                OperationType.WITHDRAW, 10000L);

        InsufficientFundsException e = assertThrows(InsufficientFundsException.class, () ->
                walletController.addOrWithdrawMoney(walletDto));

        assertEquals("Ошибка при попытке снять средства со счета. Недостаточно средств.", e.getMessage());
    }

    @Test
    @DisplayName("Test get wallet by id.")
    void getWalletDataWhenExistsTest() {
        WalletShortDto walletShortDto = walletController.getWalletData(
                UUID.fromString("b8ad159a-c5b2-4cf1-9728-ede1ab0f4434"));

        assertEquals(2222L, walletShortDto.getAmount());
    }

    @Test
    @DisplayName("Test get wallet by id when does not exist.")
    void getWalletDataWhenDoesNotExistThenThrowExceptionTest() {
        NotFoundException e = assertThrows(NotFoundException.class, () -> walletController.getWalletData(
                UUID.fromString("3422b448-2460-4fd2-9183-8000de6f8343")));

        assertEquals("Кошелек с id=3422b448-2460-4fd2-9183-8000de6f8343 не найден.", e.getMessage());
    }
}