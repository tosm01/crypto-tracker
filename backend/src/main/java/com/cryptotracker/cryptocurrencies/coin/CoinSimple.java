package com.cryptotracker.cryptocurrencies.coin;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CoinSimple {
    String id;
    String symbol;
    String name;
}
