package com.example.stockmarketgamesimulation.routes.registration;

import com.example.stockmarketgamesimulation.dto.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/register")
public class RegistrationController {
    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService){
        this.registrationService = registrationService;
    }

    @PostMapping
    public ResponseEntity<Object> registerNewUser(@RequestBody UserDTO userDTO){
        return registrationService.registerNewUser(userDTO);
    }
}
