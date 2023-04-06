package com.example.stockmarketgamesimulation.routes.users;

import com.example.stockmarketgamesimulation.dto.StockPurchaseSheetDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Object> getCurrentBalance(@AuthenticationPrincipal UserDetails userDetails){
        return userService.getCurrentBalance(userDetails);
    }

    @PostMapping("/purchase")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Object> purchaseStock(@RequestBody StockPurchaseSheetDTO stockPurchaseSheetDTO,Principal principal){
        return userService.purchaseStock(stockPurchaseSheetDTO,principal);
    }

    @GetMapping("/view/stocks")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Object> viewStocks(Principal principal){
        return userService.viewStocks(principal);
    }
}
