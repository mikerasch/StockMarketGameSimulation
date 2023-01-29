package com.example.stockmarketgamesimulation.repo;

import com.example.stockmarketgamesimulation.security.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users,Long> {
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    Users findByUsername(String username);
}
