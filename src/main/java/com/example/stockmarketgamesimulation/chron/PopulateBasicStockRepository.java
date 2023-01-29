package com.example.stockmarketgamesimulation.chron;

import com.example.stockmarketgamesimulation.repo.BasicStockRepository;
import com.example.stockmarketgamesimulation.routes.stocks.BasicStockInformation;
import com.example.stockmarketgamesimulation.routes.stocks.StockAPIService;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

@Configuration
@EnableScheduling
public class PopulateBasicStockRepository {
    private final StockAPIService stockAPIService;
    private final BasicStockRepository basicStockRepository;
    public PopulateBasicStockRepository(StockAPIService stockAPIService,BasicStockRepository basicStockRepository){
        this.stockAPIService = stockAPIService;
        this.basicStockRepository = basicStockRepository;
    }

    @PostConstruct
    public void populateOnStartup(){
        populateBasicStockRepo();
    }
    @Scheduled(cron = "0 0 0 * * ?")
    public void populateEveryDayAtMidnight(){
        populateBasicStockRepo();
    }

    public void populateBasicStockRepo(){
        basicStockRepository.deleteAll();
        List<BasicStockInformation> basicStockInformationList = stockAPIService.getAllListings();
        basicStockRepository.saveAll(basicStockInformationList);
    }
}
