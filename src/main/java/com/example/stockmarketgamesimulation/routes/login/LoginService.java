package com.example.stockmarketgamesimulation.routes.login;

import com.example.stockmarketgamesimulation.dto.UserLoginRequestDTO;
import com.example.stockmarketgamesimulation.repo.UserRepository;
import com.example.stockmarketgamesimulation.routes.users.UserLoginDTO;
import com.example.stockmarketgamesimulation.routes.users.Users;
import com.example.stockmarketgamesimulation.security.config.JwtService;
import org.springframework.http.HttpStatus;
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

    public ResponseEntity<UserLoginRequestDTO> authorizeAccount(UserLoginDTO userLoginDTO) {
        Users user = userRepository.findByEmail(userLoginDTO.getEmail());
        if(user == null || !passwordEncoder.matches(userLoginDTO.getPassword(), user.getPassword())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        String jwtToken = jwtService.generateToken(user);
        return ResponseEntity.ok(
                new UserLoginRequestDTO(jwtToken, user.getUsername(), user.getEmail())
        );
    }
}
