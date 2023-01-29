package com.example.stockmarketgamesimulation.routes.login;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {
    public final LoginService loginService;
    public LoginController(LoginService loginService){
        this.loginService = loginService;
    }
    @GetMapping
    public ResponseEntity<Object> authorizeAccount(@RequestHeader String username, @RequestHeader String password){
        return loginService.authorizeAccount(username,password);
    }
}
