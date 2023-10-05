package com.jpvp.backend.Service;

import com.jpvp.backend.Exception.TokenValidationException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtTokenService {
    @Value("${jwt.expiration}")
    private long jwtExpiration;

    @Value("${jwt.secret}")
    private String jwtSecret;

    public String generateToken(String email, HttpServletRequest request) {
        Claims claims = Jwts.claims().setSubject(email);

        long nowMillis = System.currentTimeMillis();
        long expMillis = nowMillis + jwtExpiration;
        Date expirationDate = new Date(expMillis);


        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(nowMillis))
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();

        return token;
    }

    public String extractEmail(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public String extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); //remove prefix
        }
        return null;
    }

    public String extractToken(String token) {
        if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
            return token.substring(7); //remove prefix
        }
        return null;
    }

    public <T> T getClaimFromToken (String token, Function<Claims, T> claimsSolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsSolver.apply(claims);
    }

    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
    }

    public boolean isTokenExpired(String token) {
        final Date expiration = getClaimFromToken(token, Claims::getExpiration);

        return expiration.before(new Date());
    }

    public boolean validateToken(String token, HttpServletRequest request) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
        } catch (SignatureException exception) {
            throw new TokenValidationException("Signature failed to validate");
        } catch (ExpiredJwtException exception) {
            throw new TokenValidationException("Token is expired");
        }

        final String email = extractEmail(token);

        return email != null;
    }
}
