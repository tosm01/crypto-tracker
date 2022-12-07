package com.cryptotracker.cryptocurrencies.coin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
public class CoinController {

    private final CoinService coinsService;

    @Autowired
    public CoinController(CoinService coinsService) {
        this.coinsService = coinsService;
    }

    @GetMapping("api/v1/coins/list")
    public List<Coin> getCoinsList() {
        return this.coinsService.getCoinsList();
    }
}
