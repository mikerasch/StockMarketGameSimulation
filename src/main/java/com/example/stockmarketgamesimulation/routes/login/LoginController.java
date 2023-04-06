package com.example.stockmarketgamesimulation.routes.login;

import com.example.stockmarketgamesimulation.dto.UserLoginRequestDTO;
import com.example.stockmarketgamesimulation.routes.users.UserLoginDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class LoginController {
    public final LoginService loginService;
    public LoginController(LoginService loginService){
        this.loginService = loginService;
    }
    @PostMapping
    public ResponseEntity<UserLoginRequestDTO> authorizeAccount(@RequestBody UserLoginDTO userLoginDTO){
        return loginService.authorizeAccount(userLoginDTO);
    }
}
