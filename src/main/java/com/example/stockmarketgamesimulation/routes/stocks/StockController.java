package com.example.stockmarketgamesimulation.routes.stocks;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/stocks")
public class StockController {

    private final StockService stockService;
    public StockController(StockService stockService){
        this.stockService = stockService;
    }
    @GetMapping
    public ResponseEntity<List<BasicStockInformation>> getAllListingsAvailable(){
        return stockService.getAllListings();
    }
    @GetMapping(value = "/ticker", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getPriceFromTicker(@RequestHeader String ticker){
        return stockService.getPriceFromTicker(ticker);
    }
}
