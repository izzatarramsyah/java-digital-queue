package com.example.digital_queue.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.util.Date;
import javax.crypto.SecretKey;

public class JWTUtil {
    
    private static final String SECRET_STRING = "3cbfd9b8f0a8c8c8a0d1b9c0f7ab1b9c4e7f3b6f9a9e10b3a1c65b1a9b72db9";

    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_STRING));

    public static String generateToken(String issuer) {
        return Jwts.builder()
                .setSubject(issuer)
                .setIssuer("TicketinService")  // Menyimpan penerbit token dalam claims
                .setIssuedAt(new Date()) // Set waktu pembuatan token
                .setExpiration(new Date(System.currentTimeMillis() + 3600000)) // Expiry 1 jam
                .signWith(SECRET_KEY) // ✅ Gunakan SecretKey tetap
                .compact();
    }

}
