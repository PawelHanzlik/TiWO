package ph.agh.tiwo.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import ph.agh.tiwo.entity.User;
import ph.agh.tiwo.repository.UserRepository;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
public class MyAuthenticationProviderTests {

    @InjectMocks
    MyAuthenticationProvider myAuthenticationProvider;

    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void createToken() {
        User user = User.builder().email("test").password("test").build();
        UsernamePasswordAuthenticationToken token =  new UsernamePasswordAuthenticationToken(
                user, "", new ArrayList<>());
        when(userRepository.findByEmail(token.getName())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(any(), any())).thenReturn(true);
        Authentication authenticate = myAuthenticationProvider.authenticate(token);
        assertEquals(token.getName(), authenticate.getName());
        assertEquals("", authenticate.getCredentials().toString());
    }

    @Test
    public void passwordsDontMatch() {
        User user = User.builder().email("test").password("test").build();
        UsernamePasswordAuthenticationToken token =  new UsernamePasswordAuthenticationToken(
                user, "", new ArrayList<>());
        when(userRepository.findByEmail(token.getName())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(any(), any())).thenReturn(false);
        assertThrows(BadCredentialsException.class , () -> myAuthenticationProvider.authenticate(token));
    }

    @Test
    void providerSupports(){
        assertTrue(myAuthenticationProvider.supports(UsernamePasswordAuthenticationToken.class));
    }
}
