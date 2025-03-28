package ru.example.simplewebwallet.wallet.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.example.simplewebwallet.exception.InsufficientFundsException;
import ru.example.simplewebwallet.exception.NotFoundException;
import ru.example.simplewebwallet.wallet.dto.WalletDto;
import ru.example.simplewebwallet.wallet.dto.WalletShortDto;
import ru.example.simplewebwallet.wallet.model.Wallet;
import ru.example.simplewebwallet.wallet.repository.WalletRepository;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WalletServiceImpl implements WalletService {
    WalletRepository walletRepository;

    @Override
    @Transactional
    public Wallet createWallet() {
        log.debug("Добавляем новый кошелек в базу данных.");
        return walletRepository.save(new Wallet(UUID.randomUUID(), 0L));
    }

    @Override
    @Transactional
    public WalletShortDto addOrWithdrawMoney(WalletDto walletDto) {
        log.debug("Изменяем данные кошелька {}", walletDto);
        WalletShortDto wallet = getWalletData(walletDto.getWalletId());

        return switch (walletDto.getOperationType()) {
            case DEPOSIT -> {
                Long value = wallet.getAmount() + walletDto.getAmount();
                Wallet changedWallet = walletRepository.save(new Wallet(walletDto.getWalletId(), value));
                yield new WalletShortDto(changedWallet.getAmount());
            }
            case WITHDRAW -> {
                if (wallet.getAmount() < walletDto.getAmount()) {
                    throw new InsufficientFundsException("Ошибка при попытке снять средства со счета. " +
                            "Недостаточно средств.");
                }
                Long value = wallet.getAmount() - walletDto.getAmount();
                Wallet changedWallet = walletRepository.save(new Wallet(walletDto.getWalletId(), value));
                yield new WalletShortDto(changedWallet.getAmount());
            }
        };
    }

    @Override
    @Transactional(readOnly = true)
    public WalletShortDto getWalletData(UUID walletId) {
        log.debug("Получаем данные кошелька по id={}.", walletId);
        Wallet wallet = walletRepository.findByWalletId(walletId).orElseThrow(() ->
                new NotFoundException(String.format("Кошелек с id=%s не найден.", walletId)));
        return new WalletShortDto(wallet.getAmount());
    }
}
