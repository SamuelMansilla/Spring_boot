package com.example.levelup.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

// Importa la clase específica SecretKey
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 24; // 24 horas

    // Genera un token
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    /**
     * Genera un token JWT usando la sintaxis moderna de jjwt 0.12.x.
     * Utiliza el bloque .claims() para establecer todas las propiedades.
     */
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder()
                .claims() // Inicia el bloque de claims
                .add(extraClaims) // Agrega los claims extra
                .subject(userDetails.getUsername()) // Establece el "subject" (quién es)
                .issuedAt(new Date(System.currentTimeMillis())) // Establece la fecha de emisión
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // Establece la fecha de expiración
                .and() // Cierra el bloque de claims y vuelve al builder
                .signWith(getSignInKey()) // Firma el token con la clave secreta
                .compact();
    }

    // Valida un token
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Extrae el username (email) del token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Extrae todos los claims usando la sintaxis de parser de jjwt 0.12.x.
     * El método .verifyWith() ahora funciona porque getSignInKey() devuelve SecretKey.
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSignInKey()) // <-- Ahora recibe SecretKey
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * Obtiene la SecretKey para firmar y verificar.
     * CAMBIO: El tipo de retorno ahora es SecretKey en lugar de Key.
     */
    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}