package com.jpvp.backend.Service.Token;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TokenCleanupService {

    @Autowired
    private AuthTokenManagerService authTokenManagerService;

    @Scheduled(cron = "0 0 0 * * ?")
    public void cleanupExpiredTokens() {
        Date currentDate = new Date();
        authTokenManagerService.cleanupExpiredTokens(currentDate);
    }
}

