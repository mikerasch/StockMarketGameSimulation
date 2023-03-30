package com.example.stockmarketgamesimulation.routes.registration;

import com.example.stockmarketgamesimulation.dto.UserDTO;
import com.example.stockmarketgamesimulation.repo.UserRepository;
import com.example.stockmarketgamesimulation.routes.users.Users;
import com.example.stockmarketgamesimulation.utility.ResponseHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;

@Service
public class RegistrationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public RegistrationService(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    public ResponseEntity<Object> registerNewUser(UserDTO userDTO) {
        if(userRepository.existsByUsername(userDTO.getUsername())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Username is already taken.");
        }

        if(userRepository.existsByEmail(userDTO.getEmail())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Email is already taken.");
        }
        Users users = new Users();
        users.setUsername(userDTO.getUsername());
        users.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        users.setEmail(userDTO.getEmail());
        users.setBalance(new BigDecimal(50000));
        userRepository.save(users);
        return ResponseHandler.generateResponse("Success","User added successfully",HttpStatus.OK);
    }
}
