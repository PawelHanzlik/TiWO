package ph.agh.tiwo.security.filters;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import ph.agh.tiwo.security.JwtTokenGenerator;

@ExtendWith(MockitoExtension.class)
class UsernameAndPasswordAuthenticationFilterTest {

    @Mock
    AuthenticationManager authenticationManager;

    @Mock
    JwtTokenGenerator jwtTokenGenerator;

    @InjectMocks
    EmailAndPasswordAuthenticationFilter filter;

    @Test
    public void attemptAuthentication_WhenAuthenticationProblem_ThrowException(){
        Assertions.assertThrows(RuntimeException.class, () ->
                filter.attemptAuthentication(new MockHttpServletRequest(), new MockHttpServletResponse()));
    }
}
