package com.example.stockmarketgamesimulation.routes.stocks;
import com.example.stockmarketgamesimulation.dto.StockStatsDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Page<BasicStockInformation>> getPaginationListings(@RequestHeader int page, @RequestHeader int size){
        return stockService.getAllListingsWithPagination(page, size);
    }
    @GetMapping(value = "/ticker", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<StockStatsDTO> getPriceFromTicker(@RequestHeader String ticker){
        return stockService.getPriceFromTicker(ticker);
    }

    @GetMapping(value = "/ticker/contains")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<BasicStockInformation>> getStocksSimilar(@RequestHeader String ticker) {
        return stockService.getStocksSimilar(ticker);
    }
}
