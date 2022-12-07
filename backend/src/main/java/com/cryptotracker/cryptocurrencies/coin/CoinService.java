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

    public List<CoinSimple> getCoinsList(){
        URI url = null;
        ResponseEntity<String> response = null;
        List<CoinSimple> coinsList = new ArrayList<>();

        try {
            url = new URI(BASE_URL + "/list");
            response = restTemplate.getForEntity(url, String.class);
            coinsList = objectMapper.readValue(response.getBody(), new TypeReference<ArrayList<CoinSimple>>(){});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return coinsList;
    }

    public List<CoinDetailed> getCoinsWithDetailsList(){
        URI url = null;
        ResponseEntity<String> response = null;
        List<CoinDetailed> coinsList = new ArrayList<>();

        try {
            url = new URI(BASE_URL + "/markets?vs_currency=usd&order=market_cap_desc&per_page=100&page=1&sparkline=false");
            response = restTemplate.getForEntity(url, String.class);
            coinsList = objectMapper.readValue(response.getBody(), new TypeReference<ArrayList<CoinDetailed>>(){});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return coinsList;
    }
}
