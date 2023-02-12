package com.example.stockmarketgamesimulation.repo;

import com.example.stockmarketgamesimulation.routes.stocks.UserStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserStockRepository extends JpaRepository<UserStock,Long> {

}
