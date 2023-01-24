package com.cryptotracker.cryptocurrencies.coin;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CoinDetailed extends Coin {
    String image;
    @JsonProperty("current_price")
    Double currentPrice;
    @JsonProperty("market_cap")
    Double marketCap;
    @JsonProperty("price_change_percentage_24h")
    Double priceChangePercentage24h;
}
