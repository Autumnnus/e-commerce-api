package com.kadir.jwt;

import com.kadir.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTService {

    @Value("${JWT_SECRET_KEY}")
    public String SECRET_KEY;

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claimsMap = new HashMap<>();
        User user = (User) userDetails;
        claimsMap.put("role", user.getRole());
        claimsMap.put("email", user.getEmail());
        claimsMap.put("sub", userDetails.getUsername());

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setClaims(claimsMap)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public <T> T exportToken(String token, Function<Claims, T> claimsFunc) {
        Claims claims = getClaims(token);
        T apply = claimsFunc.apply(claims);
        return apply;
    }

    public String getUserNameByToken(String token) {
        String s = exportToken(token, Claims::getSubject);
        return s;
    }

    public boolean isTokenValid(String token) {
        Date expireDate = exportToken(token, Claims::getExpiration);
        return new Date().before(expireDate);
    }

    public Claims getClaims(String token) {
        return Jwts.parser().setSigningKey(getKey())
                .build()
                .parseClaimsJws(token).getBody();
    }

    public Key getKey() {
        byte[] bytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(bytes);
    }
}
