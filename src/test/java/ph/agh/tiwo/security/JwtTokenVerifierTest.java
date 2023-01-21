package ph.agh.tiwo.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JwtTokenVerifierTest {

    JwtTokenVerifier jwtTokenVerifier =
            new JwtTokenVerifier("Axzqwe2ewfds2FGJsDlSDKEWQESJSEScxKGQWEJDSAMWEJSLDQORKSDAxzqwe2ewfds2FGJsDlSDKEWQESJSEScxKGQWEJDSAMWEJSLDQORKSD");
    String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0IiwiaWF0IjoxNjc0MzA4MzUzLCJleHAiOjE3NzQzMDgzNTN9.3BDbBFeZqI_gL5wzReidLDXRuYJYhfL45IbDRg8pHuKmRAz6DQIaVPKoc5TXBdoFUlrJCbnYfm6OH6bFgSdnUw";
    @Test
    void validateToken() {
        Jws<Claims> claimsJws = jwtTokenVerifier.validateToken(token);
        assertEquals("HS512",  claimsJws.getHeader().get("alg"));
    }
}
