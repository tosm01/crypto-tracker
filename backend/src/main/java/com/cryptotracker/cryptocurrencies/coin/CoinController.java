package com.cryptotracker.cryptocurrencies.coin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
public class CoinController {

    private final CoinService coinsService;

    @Autowired
    public CoinController(CoinService coinsService) {
        this.coinsService = coinsService;
    }

    @GetMapping("api/v1/coins/{coinId}/{currency}")
    public CoinDetailed getCoinById(@PathVariable String coinId, @PathVariable String currency) {
        return this.coinsService.getCoinById(coinId, currency);
    }

    @GetMapping("api/v1/coins/list")
    public List<Coin> getCoinsList() {
        return this.coinsService.getCoinsList();
    }

    @GetMapping("api/v1/coins/list/details/{currency}")
    public List<CoinDetailed> getDetailedCoinsList(@PathVariable String currency) {
        return this.coinsService.getDetailedCoinsList(currency);
    }

    @GetMapping("api/v1/coins/{coinId}/{currency}/graph/{days}")
    public Map<String, Object> getCoinGraphData(@PathVariable String coinId, @PathVariable String currency, @PathVariable Integer days) {
        return this.coinsService.getCoinGraphData(coinId, currency, days);
    }
}
