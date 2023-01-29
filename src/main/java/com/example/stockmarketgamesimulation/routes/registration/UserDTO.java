package com.example.stockmarketgamesimulation.routes.registration;

import lombok.Data;

@Data
public class UserDTO {
    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private String country;
    private int age;
}
