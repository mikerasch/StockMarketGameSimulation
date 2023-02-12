package com.example.stockmarketgamesimulation.routes.users;

import com.example.stockmarketgamesimulation.dto.StockPurchaseSheetDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/balance")
    public ResponseEntity<Object> getCurrentBalance(Principal principal){
        return userService.getCurrentBalance(principal);
    }

    @PostMapping("/purchase")
    public ResponseEntity<Object> purchaseStock(@RequestBody StockPurchaseSheetDTO stockPurchaseSheetDTO,Principal principal){
        return userService.purchaseStock(stockPurchaseSheetDTO,principal);
    }

    @GetMapping("/view/stocks")
    public ResponseEntity<Object> viewStocks(Principal principal){
        return userService.viewStocks(principal);
    }
}
