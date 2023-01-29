package com.example.stockmarketgamesimulation.routes.stocks;

import lombok.Data;

@Data
public class StockQuoteDTO {
    private String symbol;
    private String open;
    private String high;
    private String low;
    private String price;
    private String volume;
    private String latestTradingDay;
    private String previousClose;
    private String change;
    private String changePercent;
}
