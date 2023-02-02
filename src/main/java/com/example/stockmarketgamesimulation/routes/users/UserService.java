package com.example.stockmarketgamesimulation.routes.users;

import com.example.stockmarketgamesimulation.dto.StockPurchaseSheetDTO;
import com.example.stockmarketgamesimulation.repo.BasicStockRepository;
import com.example.stockmarketgamesimulation.repo.UserRepository;
import com.example.stockmarketgamesimulation.routes.stocks.StockAPIService;
import com.example.stockmarketgamesimulation.utility.ResponseHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.security.Principal;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BasicStockRepository basicStockRepository;
    private final StockAPIService stockAPIService;
    public UserService(UserRepository userRepository, BasicStockRepository basicStockRepository, StockAPIService stockAPIService){
        this.userRepository = userRepository;
        this.basicStockRepository = basicStockRepository;
        this.stockAPIService = stockAPIService;
    }
    public ResponseEntity<Object> getCurrentBalance(Principal principal) {
        String username = principal.getName();
        Users user = userRepository.findByUsername(username);
        BigDecimal balance = user.getBalance();
        return ResponseHandler.generateResponse("Success", balance.toString(), HttpStatus.OK);
    }

    public ResponseEntity<Object> purchaseStock(StockPurchaseSheetDTO stockPurchaseSheetDTO,Principal principal) {
        String ticker = stockPurchaseSheetDTO.getTicker();
        int amountOfShares = Integer.parseInt(stockPurchaseSheetDTO.getAmountOfShares());
        if(!basicStockRepository.existsBySymbolIgnoreCase(ticker)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ticker cannot be bought.");
        }
        if(amountOfShares <= 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Amount must be greater than 0");
        }

        double priceOfStock = Double.parseDouble(stockAPIService.getQuoteFromTicker(ticker).getPrice());
        Users user = userRepository.findByUsername(principal.getName());

        boolean canPurchase = hasFunds(priceOfStock,amountOfShares, user);
        if(!canPurchase){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid funds"); // should tell how much funds user has + how much it would have costed
        }

        double price = priceOfStock * amountOfShares;
        BigDecimal updatedPrice = user.getBalance().subtract(BigDecimal.valueOf(price));
        user.setBalance(updatedPrice);
        userRepository.save(user);
        return ResponseHandler.generateResponse("Success", "Purchase went through, total price spent: " + price + ".", HttpStatus.OK);
    }

    // todo move logic to stockservice probably
    private boolean hasFunds(double priceOfStock, int amountOfShares, Users users) {
        double price = priceOfStock * amountOfShares;
        return users.getBalance().compareTo(BigDecimal.valueOf(price)) > 0;
    }
}
