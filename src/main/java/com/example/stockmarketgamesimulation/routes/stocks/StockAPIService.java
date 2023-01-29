package com.example.stockmarketgamesimulation.routes.stocks;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class StockAPIService {
    private final String API_KEY = "TD2J5P9J75ZONVJO"; // should be env variable
    public final String BASE_URL = "https://www.alphavantage.co/query?function=";

    public List<BasicStockInformation> getAllListings(){
        // todo: change to actual api key, this is for testing purposes
        String test = "https://www.alphavantage.co/query?function=LISTING_STATUS&apikey=demo";
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(test,String.class);
        if(result == null){
            return new ArrayList<>();
        }
        return parseListingData(result.split("\n"));
    }
    public List<BasicStockInformation> parseListingData(String[] results){
        List<BasicStockInformation> basicStockInformationList = new ArrayList<>();
        for(int i = 0; i < results.length; i++){
            if(i == 0){
                continue;
            }
            BasicStockInformation basicStockInformation = new BasicStockInformation();
            String[] parseLine = results[i].split(",");
            basicStockInformation.setSymbol(parseLine[0]);
            basicStockInformation.setName(parseLine[1]);
            basicStockInformation.setExchange(parseLine[2]);
            basicStockInformation.setAssetType(parseLine[3]);
            basicStockInformation.setIpoDate(parseLine[4]);
            basicStockInformation.setDelistingDate(parseLine[5]);
            basicStockInformation.setStatus(parseLine[6]);
            basicStockInformationList.add(basicStockInformation);
        }
        return basicStockInformationList;
    }

    public String getQuoteFromTicker(String ticker) {
        String url = BASE_URL + "GLOBAL_QUOTE&symbol=" + ticker + "&apikey=" + API_KEY;
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(BASE_URL + "GLOBAL_QUOTE&symbol=" + ticker + "&apikey=" + API_KEY, String.class);
        return result;
    }
}
