package ph.agh.tiwo.security;

import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import ph.agh.tiwo.entity.User;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JwtTokenGeneratorTest {

    JwtTokenGenerator jwtTokenGenerator = new JwtTokenGenerator("10000000000000000",
            "Axzqwe2ewfds2FGJsDlSDKEWQESJSEScxKGQWEJDSAMWEJSLDQORKSDAxzqwe2ewfds2FGJsDlSDKEWQESJSEScxKGQWEJDSAMWEJSLDQORKSD");
    @Test
    void generateToken() {
        String token = jwtTokenGenerator.generateToken(
                new UsernamePasswordAuthenticationToken(
                        new User(), "", new ArrayList<>()));
        assertNotNull(token);
        assertTrue(token.length() > 15);
    }
}
