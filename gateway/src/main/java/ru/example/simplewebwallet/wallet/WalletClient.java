package ru.example.simplewebwallet.wallet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.example.simplewebwallet.client.BaseClient;
import ru.example.simplewebwallet.wallet.dto.WalletDto;

import java.util.UUID;

@Service
public class WalletClient extends BaseClient {
    private static final String API_PREFIX = "/api/v1/wallet";

    @Autowired
    public WalletClient(@Value("${SWW-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(() -> new HttpComponentsClientHttpRequestFactory())
                        .build()
        );
    }

    public ResponseEntity<Object> createWallet() {
        return post("", null);
    }

    public ResponseEntity<Object> addOrWithdrawMoney(WalletDto walletDto) {
        return patch("", walletDto);
    }

    public ResponseEntity<Object> getWalletData(UUID walletId) {
        return get("/" + walletId);
    }
}
