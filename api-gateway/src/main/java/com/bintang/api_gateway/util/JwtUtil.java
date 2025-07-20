package com.bintang.api_gateway.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    // SECRET NYA HARUS SAMA DENGAN YANG DI AUTHENTICATION-SERVICE
    public static final String SECRET = "OSAKDMNAOSKDMNSAODINASODIANSDINASODIANSDOSMADOKMQOSI";

    private Key getSignKey(){
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String validateToken(String token){
        try{
            Claims claims = extractAllClaims(token);
            return "Token is valid";
        }catch(Exception e){
            return "Token is not valid";
        }
    }


}
