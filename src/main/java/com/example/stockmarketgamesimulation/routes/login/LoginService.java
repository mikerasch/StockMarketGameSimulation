package com.example.stockmarketgamesimulation.routes.login;

import com.example.stockmarketgamesimulation.repo.UserRepository;
import com.example.stockmarketgamesimulation.security.Users;
import com.example.stockmarketgamesimulation.security.config.JwtService;
import com.example.stockmarketgamesimulation.utility.ResponseHandler;
import io.jsonwebtoken.Jwt;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class LoginService {
    public final UserRepository userRepository;
    public final PasswordEncoder passwordEncoder;
    public final JwtService jwtService;
    public LoginService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public ResponseEntity<Object> authorizeAccount(String username, String password) {
        Users user = userRepository.findByUsername(username);
        if(user == null || !passwordEncoder.matches(password,user.getPassword())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        String jwtToken = jwtService.generateToken(user);
        return ResponseHandler.generateResponse("Success",jwtToken,HttpStatus.OK);
    }
}
