package com.example.stockmarketgamesimulation.routes.users;

import com.example.stockmarketgamesimulation.dto.ProfileChangeRequestDTO;
import com.example.stockmarketgamesimulation.dto.StockPurchaseSheetDTO;
import com.example.stockmarketgamesimulation.dto.StockSellSheetDTO;
import org.springframework.http.HttpStatus;
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

    @PostMapping("/update/user")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> updateUser(@RequestBody ProfileChangeRequestDTO profileChangeRequestDTO, @AuthenticationPrincipal UserDetails userDetails) {
        return userService.updateRequest(profileChangeRequestDTO, userDetails);
    }

    @PostMapping("/sell")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> sellStock(@RequestBody StockSellSheetDTO stockSellSheetDTO, @AuthenticationPrincipal UserDetails userDetails) {
        return userService.sellStock(stockSellSheetDTO, userDetails);
    }
}
