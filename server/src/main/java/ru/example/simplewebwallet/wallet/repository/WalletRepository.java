package ru.example.simplewebwallet.wallet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.example.simplewebwallet.wallet.model.Wallet;

import java.util.Optional;
import java.util.UUID;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
    Optional<Wallet> findByWalletId(UUID uuid);
}
