package com.jpvp.backend.Service.Token;

import com.jpvp.backend.Model.AuthToken;
import com.jpvp.backend.Persistance.AuthTokenRepository;
import com.jpvp.backend.Util.EncryptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AuthTokenManagerService {
    private final EncryptionUtil encryptionUtil;
    private final AuthTokenRepository authTokenRepository;
    @Autowired
    public AuthTokenManagerService(AuthTokenRepository authTokenRepository, EncryptionUtil encryptionUtil) {
        this.authTokenRepository = authTokenRepository;
        this.encryptionUtil = encryptionUtil;
    }

    public void storeToken(String token, Long userId, Date expirationDate) {
        String encryptedToken = encryptToken(token);

        AuthToken authToken = new AuthToken();
        authToken.setToken(encryptedToken);
        authToken.setUserId(userId);
        authToken.setExpirationDate(expirationDate);
        authTokenRepository.save(authToken);
    }

    //Check if the token provided exists in the database
    public boolean isValidToken(String token) {
        return authTokenRepository.existsByToken(encryptToken(token));
    }

    public void invalidateToken(String token) {
        authTokenRepository.deleteByToken(encryptToken(token));
    }

    public void invalidateTokensForUser(Long id) {
        List<AuthToken> userTokens = authTokenRepository.findAllByUserId(id);

        authTokenRepository.deleteAll(userTokens);
    }

    public void cleanupExpiredTokens(Date currentDate) {
        List<AuthToken> expiredTokens = authTokenRepository.findAllByExpirationDateBefore(currentDate);

        authTokenRepository.deleteAll(expiredTokens);
    }

    public String encryptToken(String token) {
        return encryptionUtil.encrypt(token);
    }

}
