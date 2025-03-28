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
import ru.example.simplewebwallet.wallet.model.Wallet;

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
    @DisplayName("Test create new wallet.")
    void createWalletTest() {
        Wallet createdWallet = walletController.createWallet();

        assertEquals(0L, createdWallet.getAmount());
        assertNotNull(createdWallet.getWalletId());
    }

    @Test
    @DisplayName("Test deposit and withdraw funds.")
    void depositAndWithdrawFundsTest() {
        Wallet createdWallet = walletController.createWallet();
        WalletDto walletDto = new WalletDto(createdWallet.getWalletId(), OperationType.DEPOSIT, 1234L);
        WalletShortDto changedWallet = walletController.addOrWithdrawMoney(walletDto);

        assertEquals(1234L, changedWallet.getAmount());

        WalletDto anotherWalletDto = new WalletDto(createdWallet.getWalletId(), OperationType.WITHDRAW, 100L);
        WalletShortDto changedWallet2 = walletController.addOrWithdrawMoney(anotherWalletDto);

        assertEquals(1134L, changedWallet2.getAmount());
    }

    @Test
    @DisplayName("Test withdraw funds when insufficient funds.")
    void withdrawWhenInsufficientFundsThenTrowExceptionTest() {
        Wallet createdWallet = walletController.createWallet();
        WalletDto walletDto = new WalletDto(createdWallet.getWalletId(), OperationType.WITHDRAW, 100L);

        InsufficientFundsException e = assertThrows(InsufficientFundsException.class, () ->
                walletController.addOrWithdrawMoney(walletDto));

        assertEquals("Ошибка при попытке снять средства со счета. Недостаточно средств.", e.getMessage());
    }

    @Test
    @DisplayName("Test get wallet by id.")
    void getWalletDataWhenExistsTest() {
        Wallet createdWallet = walletController.createWallet();
        WalletShortDto walletShortDto = walletController.getWalletData(createdWallet.getWalletId());

        assertNotNull(walletShortDto);
        assertEquals(createdWallet.getAmount(), walletShortDto.getAmount());
    }

    @Test
    @DisplayName("Test get wallet by id when does not exist.")
    void getWalletDataWhenDoesNotExistThenThrowExceptionTest() {
        NotFoundException e = assertThrows(NotFoundException.class, () -> walletController.getWalletData(
                UUID.fromString("3422b448-2460-4fd2-9183-8000de6f8343")));

        assertEquals("Кошелек с id=3422b448-2460-4fd2-9183-8000de6f8343 не найден.", e.getMessage());
    }
}