package com.example.stockmarketgamesimulation.security.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    private final String SECRET_KEY;
    private final EncryptionService encryptionService;
    public JwtService(EncryptionService encryptionService) throws NoSuchAlgorithmException {
        this.encryptionService = encryptionService;
        SecureRandom secureRandom = SecureRandom.getInstanceStrong();
        byte[] bytes = new byte[128];
        secureRandom.nextBytes(bytes);
        StringBuilder stringBuilder = new StringBuilder();
        for(byte i: bytes){
            stringBuilder.append(String.format("%02x",i));
        }
        SECRET_KEY = stringBuilder.toString();
    }
    public String extractUsername(String jwtToken) {
        return extractClaim(jwtToken,Claims::getSubject);
    }

    public <T> T extractClaim(String jwtToken, Function<Claims,T> resolver){
        jwtToken = encryptionService.decrypt(jwtToken);
        Claims claims = getAllClaims(jwtToken);
        return resolver.apply(claims);
    }
    private Claims getAllClaims(String jwtToken){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();
    }

    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(),userDetails);
    }
    public String generateToken(Map<String,Object> claims, UserDetails userDetails){
        String token = Jwts
                .builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24))) // 1 day
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
        return encryptionService.encrypt(token);
    }
    private Key getSignInKey() {
        byte[] bytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(bytes);
    }
    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token,Claims::getExpiration);
    }
}
