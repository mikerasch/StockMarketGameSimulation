package com.example.stockmarketgamesimulation.routes.registration;

import com.example.stockmarketgamesimulation.security.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistrationRepository extends JpaRepository<Users,Long> {
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
}
