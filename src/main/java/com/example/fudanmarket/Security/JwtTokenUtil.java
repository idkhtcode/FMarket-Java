package com.example.fudanmarket.Security;

import com.example.fudanmarket.Entity.User;
import com.example.fudanmarket.Repository.UserRepository;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;

@Slf4j
@Component
public class JwtTokenUtil implements Serializable {
    private final int validity = 3600000;
    private final String secret = "Fudan2022";
    @Autowired
    UserRepository userRepository;

    public JwtTokenUtil() {
    }

    public String generateToken(User user) {
        JwtBuilder jwtBuilder = Jwts.builder();
        return jwtBuilder
                .setHeaderParam("type", "JWT")
                .setHeaderParam("alg", "HS512")
                .claim("username", user.getUsername())
                .claim("id", user.getId())
                .setSubject("superAdmin")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + validity))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public User parse(String token) {
        JwtParser parser = Jwts.parser();
        Jws<Claims> claimsJws = parser.setSigningKey(secret).parseClaimsJws(token);
        Claims claims = claimsJws.getBody();

        return userRepository.findByUsername((String) claims.get("username"));
    }

    public boolean verify(String token) {
        Claims claims;
        String username;
        try {
            claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
            username = claims.get("username").toString();
            if (!userRepository.existsByUsername(username)) {
                log.error("user " + username + " token invalid");
                return false;
            }
            return true;
        } catch (ExpiredJwtException j) {
            log.error("token out of date");
            log.error(j.getMessage());
        } catch (Exception e) {
            log.error("token invalid");
            log.error(e.getMessage());
        }
        return false;
    }
}
