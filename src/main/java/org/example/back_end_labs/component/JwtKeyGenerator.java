package org.example.back_end_labs.component;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

//class for generating a secret key
public class JwtKeyGenerator {
    public static void Generate() {
        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
        System.out.println(java.util.Base64.getEncoder().encodeToString(key.getEncoded()));
    }
}