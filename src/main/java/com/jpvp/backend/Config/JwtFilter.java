package com.jpvp.backend.Config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpvp.backend.Exception.ErrorResponse;
import com.jpvp.backend.Exception.TokenValidationException;
import com.jpvp.backend.Service.AuthTokenManagerService;
import com.jpvp.backend.Service.UserService;
import com.jpvp.backend.Service.JwtTokenService;
import com.jpvp.backend.Util.EncryptionUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private AuthTokenManagerService authTokenManagerService;

    @Autowired
    private EncryptionUtil encryptionUtil;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${jwt.secret}")
    private String jwtSecret;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String token = jwtTokenService.extractToken(request);
        if (StringUtils.hasText(token)) {
            try {
                if (jwtTokenService.validateToken(token, request)) {
                    if(!authTokenManagerService.isValidToken(token)) {
                        throw new TokenValidationException("Token is not in repository");
                    }

                    System.out.println("Token validation successful"); //delete
                    Claims claims = Jwts.parser()
                            .setSigningKey(jwtSecret)
                            .parseClaimsJws(token)
                            .getBody();

                    String email = claims.getSubject();

                    Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, Collections.emptyList());

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (TokenValidationException exception){
                response.setStatus(HttpStatus.BAD_REQUEST.value());
                response.setContentType("application/json");
                response.getWriter().write(objectMapper.writeValueAsString(
                        new ErrorResponse("TokenValidationException", exception.getMessage())));
                return;
            }
        }
        filterChain.doFilter(request, response);
    }
}
