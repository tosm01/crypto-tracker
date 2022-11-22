package com.cryptotracker.cryptocurrencies.coin;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Service
public class CoinService {
private static final String BASE_URL = "https://api.coingecko.com/api/v3/coins";

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public CoinService(RestTemplateBuilder restTemplateBuilder, ObjectMapper objectMapper) {
        this.restTemplate = restTemplateBuilder.build();
        this.objectMapper = objectMapper;
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
}
