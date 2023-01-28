package com.cryptotracker.cryptocurrencies.coin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CoinService {
    private static final String BASE_URL = "https://api.coingecko.com/api/v3/coins";

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public CoinService(RestTemplateBuilder restTemplateBuilder, ObjectMapper objectMapper) {
        this.restTemplate = restTemplateBuilder.build();
        this.objectMapper = objectMapper;
    }

    public CoinDetailed getCoinById(String coinId, String currency){
        URI url = null;
        ResponseEntity<String> response = null;
        CoinDetailed coin = new CoinDetailed();

        try {
            url = new URI(BASE_URL + "/" + coinId);
            response = restTemplate.getForEntity(url, String.class);
            coinDetailedMapper(currency, response, coin);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return coin;
    }

    public List<Coin> getCoinsList(){
        URI url = null;
        ResponseEntity<String> response = null;
        List<Coin> coinsList = new ArrayList<>();

        try {
            url = new URI(BASE_URL + "/list");
            response = restTemplate.getForEntity(url, String.class);
            coinsList = objectMapper.readValue(response.getBody(), new TypeReference<ArrayList<Coin>>(){});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return coinsList;
    }

    public List<CoinDetailed> getDetailedCoinsList(String currency){
        URI url = null;
        ResponseEntity<String> response = null;
        List<CoinDetailed> coinsList = new ArrayList<>();

        try {
            url = new URI(BASE_URL + "/markets?vs_currency=" + currency + "&order=market_cap_desc&sparkline=false");
            response = restTemplate.getForEntity(url, String.class);
            coinsList = objectMapper.readValue(response.getBody(), new TypeReference<ArrayList<CoinDetailed>>(){});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return coinsList;
    }

    public Map<String, Object> getCoinGraphData(String coinId, String currency, Integer days){
        URI url = null;
        ResponseEntity<String> response = null;
        HashMap<String, Object> result = new HashMap<String, Object>();

        try {
            url = new URI(BASE_URL + "/" + coinId + "/market_chart?vs_currency=" + currency + "&days=" + days);
            response = restTemplate.getForEntity(url, String.class);
            result = objectMapper.readValue(response.getBody(), new TypeReference<>(){});
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;

    }

    private void coinDetailedMapper(String currency, ResponseEntity<String> response, CoinDetailed coin) throws JsonProcessingException {
        Map<String, Object> responseBody = objectMapper.readValue(response.getBody(), new TypeReference<>(){});
        coin.id = responseBody.get("id").toString();
        coin.symbol = responseBody.get("symbol").toString();
        coin.name = responseBody.get("name").toString();

        Map<String, Object> imageMap = (Map<String, Object>) responseBody.get("image");
        coin.image = imageMap.get("large").toString();

        Map<String, Object> market_data_map = (Map<String, Object>) responseBody.get("market_data");

        Map<String, Object> current_price_map = (Map<String, Object>) market_data_map.get("current_price");
        coin.currentPrice = Double.valueOf(current_price_map.get(currency).toString());

        Map<String, Object> market_cap_map = (Map<String, Object>) market_data_map.get("market_cap");
        coin.marketCap = Double.valueOf(market_cap_map.get(currency).toString());

        Map<String, Object> price_change_percentage_24h_map = (Map<String, Object>) market_data_map.get("price_change_percentage_24h_in_currency");
        coin.priceChangePercentage24h = Double.valueOf(price_change_percentage_24h_map.get(currency).toString());
    }
}
