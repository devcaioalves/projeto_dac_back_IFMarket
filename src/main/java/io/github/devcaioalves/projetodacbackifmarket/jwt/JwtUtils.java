package io.github.devcaioalves.projetodacbackifmarket.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Slf4j
public class JwtUtils {

    public static final String JWT_BEARER = "Bearer ";
    public static final String JWT_AUTHORIZATION = "Authorization";
    public static final String SECRET_KEY = "0123456789-0123456789-0123456789"; // chave secreta
    public static final long EXPIRE_DAYS = 0;
    public static final long EXPIRE_HOURS = 1;
    public static final long EXPIRE_MINUTES = 30;

    private JwtUtils() {}

    private static javax.crypto.SecretKey generateKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    private static Date toExpireDate(Date start) {
        LocalDateTime dateTime = start.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime end = dateTime.plusDays(EXPIRE_DAYS).plusHours(EXPIRE_HOURS).plusMinutes(EXPIRE_MINUTES);
        return Date.from(end.atZone(ZoneId.systemDefault()).toInstant());
    }

    // Gera token com id, email e role
    public static JwtToken gerarToken(Long idUsuario, String email, String role) {
        Date issuedAt = new Date();
        Date limit = toExpireDate(issuedAt);
        String token = Jwts.builder()
                .setHeaderParam("typ", "JWT") // cabeçalho correto
                .setSubject(email)            // subject = email
                .claim("id", idUsuario)       // claim extra
                .claim("role", role)          // claim extra
                .setIssuedAt(issuedAt)
                .setExpiration(limit)
                .signWith(generateKey())
                .compact();
        return new JwtToken(token);
    }

    private static Claims getClaimsFromToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(generateKey())
                    .build()
                    .parseClaimsJws(refactorToken(token))
                    .getBody();
        } catch (JwtException ex) {
            log.error("Token inválido {}", ex.getMessage());
        }
        return null;
    }

    // Retorna o email (subject)
    public static String getEmailFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims != null ? claims.getSubject() : null;
    }

    // Retorna a role (claim extra)
    public static String getRoleFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims != null ? claims.get("role", String.class) : null;
    }

    public static boolean isTokenValid(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(generateKey())
                    .build()
                    .parseClaimsJws(refactorToken(token));
            return true;
        } catch (JwtException ex) {
            log.error("Token inválido. {}", ex.getMessage());
        }
        return false;
    }

    public static String refactorToken(String token) {
        return token.replace(JWT_BEARER, "");
    }
}