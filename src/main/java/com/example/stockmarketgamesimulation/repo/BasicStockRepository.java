package com.example.stockmarketgamesimulation.repo;

import com.example.stockmarketgamesimulation.routes.stocks.BasicStockInformation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BasicStockRepository extends JpaRepository<BasicStockInformation,Long> {
    boolean existsBySymbolIgnoreCase(String ticker);
    BasicStockInformation findBySymbolIgnoreCase(String ticker);
    List<BasicStockInformation> findFirst10BySymbolIsContainingIgnoreCase(String ticker);
}
