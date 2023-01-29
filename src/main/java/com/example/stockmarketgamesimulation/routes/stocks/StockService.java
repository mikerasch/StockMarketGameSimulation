package com.example.stockmarketgamesimulation.routes.stocks;

import com.example.stockmarketgamesimulation.repo.BasicStockRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class StockService {
    private final BasicStockRepository basicStockRepository;
    private final StockAPIService stockAPIService;
    public StockService(BasicStockRepository basicStockRepository,StockAPIService stockAPIService){
        this.basicStockRepository = basicStockRepository;
        this.stockAPIService = stockAPIService;
    }
    public ResponseEntity<List<BasicStockInformation>> getAllListings() {
        List<BasicStockInformation> basicStockInformationList = basicStockRepository.findAll();
        return new ResponseEntity<>(basicStockInformationList,HttpStatus.OK);
    }

    public ResponseEntity<String> getPriceFromTicker(String ticker) {
        if(!basicStockRepository.existsBySymbolIgnoreCase(ticker)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Stock ticker could not be found!");
        }
        String result = stockAPIService.getQuoteFromTicker(ticker);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }
}
