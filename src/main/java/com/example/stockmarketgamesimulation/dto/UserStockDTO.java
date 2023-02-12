package com.example.stockmarketgamesimulation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserStockDTO {
    private String Ticker;
    private String assetType;
    private String companyName;
    private String exchange;
    private int amountOfShares;
    private double worth;

}
