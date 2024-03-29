package com.example.stockmarketgamesimulation.routes.users;

import com.example.stockmarketgamesimulation.dto.*;
import com.example.stockmarketgamesimulation.repo.BasicStockRepository;
import com.example.stockmarketgamesimulation.repo.UserRepository;
import com.example.stockmarketgamesimulation.repo.UserStockRepository;
import com.example.stockmarketgamesimulation.routes.stocks.BasicStockInformation;
import com.example.stockmarketgamesimulation.routes.stocks.StockAPIService;
import com.example.stockmarketgamesimulation.routes.stocks.UserStock;
import com.example.stockmarketgamesimulation.utility.ResponseHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BasicStockRepository basicStockRepository;
    private final StockAPIService stockAPIService;
    private final UserStockRepository userStockRepository;
    public UserService(UserRepository userRepository, BasicStockRepository basicStockRepository, StockAPIService stockAPIService, UserStockRepository userStockRepository){
        this.userRepository = userRepository;
        this.basicStockRepository = basicStockRepository;
        this.stockAPIService = stockAPIService;
        this.userStockRepository = userStockRepository;
    }
    public ResponseEntity<Object> getCurrentBalance(UserDetails userService) {
        Users user = userRepository.findByEmail(userService.getUsername());
        BigDecimal balance = user.getBalance();
        return ResponseHandler.generateResponse("Success", balance.toString(), HttpStatus.OK);
    }

    public ResponseEntity<Object> purchaseStock(StockPurchaseSheetDTO stockPurchaseSheetDTO,Principal principal) {
        String ticker = stockPurchaseSheetDTO.ticker();
        int amountOfShares = Integer.parseInt(stockPurchaseSheetDTO.amountOfShares());
        BasicStockInformation basicStockInformation = basicStockRepository.findBySymbolIgnoreCase(ticker);
        if(basicStockInformation == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ticker cannot be bought.");
        }
        if(amountOfShares <= 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Amount must be greater than 0");
        }

        double priceOfStock = Double.parseDouble(stockAPIService.getQuoteFromTicker(ticker).getPrice());
        Users user = userRepository.findByEmail(principal.getName());

        boolean canPurchase = hasFunds(priceOfStock,amountOfShares, user);
        if(!canPurchase){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid funds"); // should tell how much funds user has + how much it would have costed
        }

        double price = priceOfStock * amountOfShares;
        BigDecimal updatedPrice = user.getBalance().subtract(BigDecimal.valueOf(price));
        user.setBalance(updatedPrice);
        userRepository.save(user);
        saveStockInformation(user,basicStockInformation,amountOfShares);
        return ResponseHandler.generateResponse("Success", "Purchase went through, total price spent: " + price + ".", HttpStatus.OK);
    }

    private void saveStockInformation(Users user, BasicStockInformation basicStockInformation, int amountOfShares) {
        UserStock userStock = userStockRepository.findByUserIdAndStockId(user.getId(), basicStockInformation.getId());
        if(userStock == null) {
            userStock = new UserStock(user,basicStockInformation,amountOfShares);
            userStockRepository.save(userStock);
            return;
        }
        updateStockInformation(amountOfShares,userStock);
    }

    private void updateStockInformation(int amountOfShares, UserStock userStock) {
        userStock.setQuantity(userStock.getQuantity() + amountOfShares);
        userStockRepository.save(userStock);
    }

    // todo move logic to stockservice probably
    private boolean hasFunds(double priceOfStock, int amountOfShares, Users users) {
        double price = priceOfStock * amountOfShares;
        return users.getBalance().compareTo(BigDecimal.valueOf(price)) > 0;
    }

    public ResponseEntity<Object> viewStocks(Principal principal) {
        Users user = userRepository.findByEmail(principal.getName());
        List<UserStock> foo = user.getStocks();
        List<UserStockDTO> userStockDTOS = new ArrayList<>();
        for(UserStock userStock: foo){
            BasicStockInformation basicStockInformation = userStock.getBasicStockInformation();
            StockStatsDTO stockStats = stockAPIService.getQuoteFromTicker(basicStockInformation.getSymbol());
            double worth = Double.parseDouble(stockStats.getPrice()) * userStock.getQuantity();
            userStockDTOS.add(new UserStockDTO(
                    basicStockInformation.getSymbol(),
                    basicStockInformation.getAssetType(),
                    basicStockInformation.getName(),
                    basicStockInformation.getExchange(),
                    userStock.getQuantity(),
                    worth
            ));
        }
        return ResponseEntity.ok(userStockDTOS);
    }

    public ResponseEntity<String> updateRequest(ProfileChangeRequestDTO profileChangeRequestDTO, UserDetails userDetails) {
        Users user = null;
        if(!userDetails.getUsername().equals(profileChangeRequestDTO.email())) {
            user = changeEmail(profileChangeRequestDTO);
        }
        return changeUsername(profileChangeRequestDTO, user);
    }

    private ResponseEntity<String> changeUsername(ProfileChangeRequestDTO profileChangeRequestDTO, Users user) {
        user.setUsername(profileChangeRequestDTO.username());
        userRepository.save(user);
        return ResponseEntity.ok("Changed profile successfully");
    }

    private Users changeEmail(ProfileChangeRequestDTO profileChangeRequestDTO) {
        Users user = userRepository.findByEmail(profileChangeRequestDTO.email());
        if(user != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not update user information");
        }

        user.setEmail(profileChangeRequestDTO.email());
        return user;
    }

    public ResponseEntity<String> sellStock(StockSellSheetDTO stockSellSheetDTO, UserDetails userDetails) {
        Users user = userRepository.findByEmail(userDetails.getUsername());
        if(user == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not sell stock!");
        }
        StockStatsDTO statsDTO = stockAPIService.getQuoteFromTicker(stockSellSheetDTO.getTicker());
        BigDecimal priceOfStock = BigDecimal.valueOf(Double.parseDouble(statsDTO.getPrice()));
        BigDecimal totalPriceOfTransaction = priceOfStock.multiply(BigDecimal.valueOf(stockSellSheetDTO.getAmount()));
        UserStock userStock = userStockRepository.findByUserId(user.getId());
        if(stockSellSheetDTO.getAmount() > userStock.getQuantity()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: Trying to sell more shares as compared to what you own");
        }
        userStock.setQuantity(userStock.getQuantity() - stockSellSheetDTO.getAmount());
        if(userStock.getQuantity() <= 0) {
            userStockRepository.delete(userStock);
        }
        else {
            userStockRepository.save(userStock);
        }
        user.setBalance(user.getBalance().add(totalPriceOfTransaction));
        userRepository.save(user);
        return ResponseEntity.ok("Successfully sold stock");
    }
}
