package com.jpvp.backend.Util;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.util.Base64;

public class GenerateSecureKey {
    public static byte[] keyBytes = Keys.secretKeyFor(SignatureAlgorithm.HS256).getEncoded();
    public static String jwtSecret = Base64.getEncoder().encodeToString(keyBytes);
}
