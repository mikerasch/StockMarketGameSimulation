package com.example.stockmarketgamesimulation.repo;

import com.example.stockmarketgamesimulation.routes.stocks.UserStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserStockRepository extends JpaRepository<UserStock,Long> {
    @Query("SELECT us FROM UserStock us WHERE us.user.id =:userId AND us.basicStockInformation.id =:stockId")
    UserStock findByUserIdAndStockId(@Param("userId") Long userId, @Param("stockId") Long stockId);
}
