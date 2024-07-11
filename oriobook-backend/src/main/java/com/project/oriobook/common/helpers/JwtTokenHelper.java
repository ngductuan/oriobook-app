package com.project.oriobook.common.helpers;

import com.project.oriobook.common.constants.CommonConst;
import com.project.oriobook.modules.user.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;

@Component
public class JwtTokenHelper {
    @Value("${jwt.access-token-time}")
    private long accessTokenTime;

    @Value("${jwt.refresh-token-time}")
    private long refreshTokenTime;

    @Value("${jwt.secret-key}")
    private String secretKey;

    public String generateToken(User user, String mode){
        long tokenTime = mode.equals(CommonConst.REFRESH) ? refreshTokenTime : accessTokenTime;
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", user.getEmail());

        String token = Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getEmail())
                .setExpiration(new Date(System.currentTimeMillis() + tokenTime * 1000L))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();

        return token;
    }

    private Key getSignInKey(){
        byte[] bytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(bytes);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = this.extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public boolean isTokenExpired(String token) {
        Date expirationDate = this.extractClaim(token, Claims::getExpiration);
        return expirationDate.before(new Date());
    }

    public LocalDateTime getExpirationFromToken(String token){
        Date expirationDate = this.extractClaim(token, Claims::getExpiration);
        return new java.sql.Timestamp(expirationDate.getTime()).toLocalDateTime();
    }
}
