package com.example.stockmarketgamesimulation.repo;

import com.example.stockmarketgamesimulation.routes.stocks.BasicStockInformation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BasicStockRepository extends JpaRepository<BasicStockInformation,Long> {
    boolean existsBySymbolIgnoreCase(String ticker);
}
