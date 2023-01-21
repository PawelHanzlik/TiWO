package ph.agh.tiwo.security.filters;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.impl.DefaultClaims;
import io.jsonwebtoken.impl.DefaultJws;
import io.jsonwebtoken.impl.DefaultJwsHeader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;
import ph.agh.tiwo.security.JwtTokenVerifier;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class JwtTokenVerifierFilterTest {

    @Mock
    JwtTokenVerifier jwtTokenVerifier;

    @Mock
    HttpServletRequest httpServletRequest;

    @Mock
    HttpServletResponse httpServletResponse;

    @Mock
    FilterChain filterChain;

    @InjectMocks
    JwtTokenVerifierFilter jwtTokenVerifierFilter;

    @Test
    public void shouldNotFilter_LoginPath_Outfiltered() {
        Mockito.when(httpServletRequest.getServletPath()).thenReturn("/login");
        assertTrue(jwtTokenVerifierFilter.shouldNotFilter(httpServletRequest));
    }

    @Test
    public void shouldNotFilter_NonLoginPath_filtered() {
        Mockito.when(httpServletRequest.getServletPath()).thenReturn("/login2");
        assertFalse(jwtTokenVerifierFilter.shouldNotFilter(httpServletRequest));
    }

    @Test
    public void getJwtFromRequest_WithoutAuthorizationHeader_returnToken() {
        Mockito.when(httpServletRequest.getHeader("Authorization")).thenReturn("Token");
        assertEquals("Token", jwtTokenVerifierFilter.getJwtFromRequest(httpServletRequest));
    }

    @Test
    public void getJwtFromRequest_WithAuthorizationHeader_returnBearerToken() {
        Mockito.when(httpServletRequest.getHeader("Authorization")).thenReturn("Bearer Token");
        assertEquals("Token", jwtTokenVerifierFilter.getJwtFromRequest(httpServletRequest));
    }

    @Test
    public void doFilterInternal_ExpiredToken_ThrowException() {
        Mockito.when(httpServletRequest.getHeader("Authorization")).thenReturn("Bearer Token");
        Mockito.when(jwtTokenVerifier.validateToken(Mockito.anyString())).thenThrow(JwtException.class);
        assertThrows(BadCredentialsException.class, () -> jwtTokenVerifierFilter.doFilterInternal(httpServletRequest, httpServletResponse, filterChain));
    }

    @Test
    public void doFilterInternal_ValikToken_filter() throws ServletException, IOException {
        Mockito.when(httpServletRequest.getHeader("Authorization")).thenReturn("Bearer Token");
        DefaultJws<Claims> claims = new DefaultJws<>(new DefaultJwsHeader(Map.of("alg", "HS512")), new DefaultClaims(Map.of("sub", "peter")), "HS512");
        Mockito.when(jwtTokenVerifier.validateToken(Mockito.anyString())).thenReturn(claims);
        jwtTokenVerifierFilter.doFilterInternal(httpServletRequest, httpServletResponse, filterChain);
        Mockito.verify(filterChain).doFilter(httpServletRequest, httpServletResponse);
    }

}
