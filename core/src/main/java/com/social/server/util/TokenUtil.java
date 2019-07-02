package com.social.server.util;

import com.social.server.dto.UserDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Slf4j
public class TokenUtil {

    private final static String SECRET_KEY = "SOCIAL_SECRET_KEY";
    private final static int LIVE_TIME_TOKEN = 10; // 10 days
    private final static String TOKEN_PREFIX = "Bearer";

    public static UserDto parseToken(String token) {
        try {
            Claims body = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .getBody();
            UserDto u = new UserDto();
            u.setId(((Integer)body.get("userId")).longValue());
            u.setEmail(body.getSubject());
            return u;
        } catch (JwtException | ClassCastException e) {
            log.error("Parse token was failed", e);
            throw e;
        }
    }

    public static String generateToken(UserDto u) {
        return generateToken(u.getId(), u.getEmail());
    }

    public static String generateToken(long id, String email) {
        Claims claims = Jwts.claims().setSubject(email);
        claims.put("userId", id);
        return TOKEN_PREFIX + " " + Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .setExpiration(Timestamp.valueOf(LocalDateTime.now().plusDays(LIVE_TIME_TOKEN)))
                .compact();
    }
}
