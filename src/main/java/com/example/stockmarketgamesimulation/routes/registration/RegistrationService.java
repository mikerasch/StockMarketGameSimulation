package com.example.stockmarketgamesimulation.routes.registration;

import com.example.stockmarketgamesimulation.security.Users;
import com.example.stockmarketgamesimulation.utility.ResponseHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class RegistrationService {

    private final RegistrationRepository registrationRepository;
    private final PasswordEncoder passwordEncoder;
    public RegistrationService(RegistrationRepository registrationRepository, PasswordEncoder passwordEncoder){
        this.registrationRepository = registrationRepository;
        this.passwordEncoder = passwordEncoder;
    }
    public ResponseEntity<Object> registerNewUser(UserDTO userDTO) {
        if(registrationRepository.existsByUsername(userDTO.getUsername())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Username is already taken.");
        }

        if(registrationRepository.existsByEmail(userDTO.getEmail())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Email is already taken.");
        }
        Users users = new Users();
        users.setUsername(userDTO.getUsername());
        users.setFirstName(userDTO.getFirstName());
        users.setLastName(userDTO.getLastName());
        users.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        users.setEmail(userDTO.getEmail());
        users.setCountry(userDTO.getCountry());
        users.setAge(userDTO.getAge());

        registrationRepository.save(users);
        return ResponseHandler.generateResponse("Success","User added successfully",HttpStatus.OK);
    }
}
