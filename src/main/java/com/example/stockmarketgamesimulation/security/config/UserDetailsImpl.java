package com.example.stockmarketgamesimulation.security.config;

import com.example.stockmarketgamesimulation.repo.UserRepository;
import com.example.stockmarketgamesimulation.routes.users.Users;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
public class UserDetailsImpl implements UserDetailsService {
    private final UserRepository userRepository;
    public UserDetailsImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username){
        Users users = userRepository.findByUsername(username);
        if(users != null){
            return users;
        }
        throw new UsernameNotFoundException("Username can not be found!");
    }
}
