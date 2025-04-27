package com.example.mobilear.jwt;

import com.example.mobilear.entity.Account;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
@Slf4j
public class JwtTokenProvider {
    // Đoạn JWT_SECRET này là bí mật, chỉ có phía server biết
    private final Key JWT_SECRET = Keys.secretKeyFor(SignatureAlgorithm.HS512);;

    //Thời gian có hiệu lực của chuỗi jwt
    private static final long ONE_SECOND = 1000L;
    private final long JWT_EXPIRATION = ONE_SECOND * 60 * 30; // 30 minutes

    // Tạo ra jwt từ thông tin user
    public String generateToken(Account account) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);
        return Jwts.builder()
                .setSubject(account.getUsername() + " " + account.getPassword())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();
    }

    // Lấy thông tin user từ jwt
    public String getUsernameFromJwt(String token) {
        Jws<Claims> claimsJws = Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .build() // 💡 Bắt buộc phải gọi build() trước
                .parseClaimsJws(token);
        String subject = claimsJws.getBody().getSubject();
        return subject.split(" ")[0];
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(JWT_SECRET).build().parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
        }
        return false;
    }
}
