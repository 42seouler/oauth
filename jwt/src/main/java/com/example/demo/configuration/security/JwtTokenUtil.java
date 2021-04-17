package com.example.demo.configuration.security;

import com.example.demo.domain.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static java.lang.String.format;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtTokenUtil {

    private final SecretKey jwtSecret = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private final String jwtIssuer = "nakim";

    public String generateAccessToken(User user) {

        Map<String, Object> header = new HashMap<>();
        header.put("alg", "HS256");
        header.put("typ", "JWT");

        Map<String, Object> content = new HashMap<>();
        content.put("username", user.getUsername());
        content.put("fullName", "what");

        return Jwts.builder()
                .setHeader(header)
                .setSubject("42 OAuth Token")
                .setIssuer(jwtIssuer)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000))
                .addClaims(content)
                .signWith(jwtSecret)
                .compact();
    }

    public String getUserId(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(jwtSecret)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.get("fullName", String.class);
    }

    public String getUsername(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(jwtSecret)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.get("username", String.class);
    }

    public Date getExpirationDate(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(jwtSecret)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getExpiration();
    }

    /**
     *
     * @param token jws String looks like this:
     *              eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJKb2UifQ.1KP0SsvENi7Uz1oQc07aXTL7kpQG5jBNIybqr60AlD4
     * @return 토큰이 유효하면 True, 아니면 False
     */
    public boolean validate(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(jwtSecret).build().parseClaimsJws(token);
            return true;
        } catch (SignatureException exception) {
            log.error("Invalid JWT signature - {}", exception.getMessage());
        } catch (MalformedJwtException exception) {
            log.error("Invalid JWT token - {}", exception.getMessage());
        } catch (ExpiredJwtException exception) {
            log.error("Expired JWT token - {}", exception.getMessage());
        } catch (UnsupportedJwtException exception) {
            log.error("Unsupported JWT token - {}", exception.getMessage());
        } catch (IllegalArgumentException exception) {
            log.error("JWT claims is empty - {}", exception.getMessage());
        }
        return false;
    }
}
