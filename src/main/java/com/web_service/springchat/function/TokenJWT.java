package com.web_service.springchat.function;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.UUID;

public class TokenJWT {
    public static String create(String subject) {

        //The JWT signature algorithm we will be using to sign the token
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        //We will sign our JWT with our ApiKey secret
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary("SECRET_KEY");
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        //Let's set the JWT Claims
        JwtBuilder builder = Jwts.builder().setId(String.valueOf(UUID.randomUUID()))
                .setIssuedAt(now)
                .setSubject(subject)
                .setIssuer("SpringChatAPI")
                .signWith(signatureAlgorithm, signingKey);

        //if it has been specified, let's add the expiration
//        if (ttlMillis > 0) {
//            long expMillis = nowMillis + ttlMillis;
//            Date exp = new Date(expMillis);
//            builder.setExpiration(exp);
//        }

        //Builds the JWT and serializes it to a compact, URL-safe string
        return builder.compact();
    }

    public static Claims decode(String jwt) {
        //This line will throw an exception if it is not a signed JWS (as expected)
        return  Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary("SECRET_KEY"))
                .parseClaimsJws(jwt).getBody();
    }
}
