package com.example.stockmarketgamesimulation.routes.stocks;

import com.example.stockmarketgamesimulation.dto.StockStatsDTO;
import com.example.stockmarketgamesimulation.repo.BasicStockRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    public ResponseEntity<Page<BasicStockInformation>> getAllListingsWithPagination(int page,int size ) {
        Page<BasicStockInformation> basicStockInformationList = basicStockRepository.findAll(PageRequest.of(page,size));
        return new ResponseEntity<>(basicStockInformationList,HttpStatus.OK);
    }

    public ResponseEntity<StockStatsDTO> getPriceFromTicker(String ticker) {
        if(!basicStockRepository.existsBySymbolIgnoreCase(ticker)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Stock ticker could not be found!");
        }
        StockStatsDTO result = stockAPIService.getQuoteFromTicker(ticker);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    public ResponseEntity<List<BasicStockInformation>> getStocksSimilar(String ticker) {
        List<BasicStockInformation> basicStockInformationList = basicStockRepository.findFirst10BySymbolIsContainingIgnoreCase(ticker);
        return ResponseEntity.ok(basicStockInformationList);
    }
}
