package ph.agh.tiwo.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;

@Component
public class JwtTokenGenerator {

    private final String tokenExpirationAfterDays;

    private final String secretKey;

    public JwtTokenGenerator(@Value("${jwt.token.expiration}") String tokenExpirationAfterDays, @Value("${jwt.token.key}") String secretKey) {
        this.tokenExpirationAfterDays = tokenExpirationAfterDays;
        this.secretKey = secretKey;
    }

    public String generateToken(Authentication authentication){
        return Jwts.builder()
                .setSubject(authentication.getName())
                .setIssuedAt(new Date())
                .setExpiration(Date.from(Instant.now().plusMillis(Long.parseLong(tokenExpirationAfterDays))))
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .compact();
    }
}
