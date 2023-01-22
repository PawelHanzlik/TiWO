package ph.agh.tiwo.IntegrationTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;
import ph.agh.tiwo.controller.UserController;
import ph.agh.tiwo.exception.Classes.UserAlreadyExistsException;
import ph.agh.tiwo.repository.ProductListRepository;
import ph.agh.tiwo.repository.ProductRepository;
import ph.agh.tiwo.repository.UserRepository;
import ph.agh.tiwo.security.GlobalSecurityConfiguration;
import ph.agh.tiwo.security.JwtTokenGenerator;
import ph.agh.tiwo.security.JwtTokenVerifier;
import ph.agh.tiwo.security.filters.EmailAndPasswordAuthenticationFilter;
import ph.agh.tiwo.service.ProductListService;
import ph.agh.tiwo.service.ProductService;
import ph.agh.tiwo.service.UserService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ph.agh.tiwo.DataProviders.UserIntegrationTestsDataProvider.*;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerIntegrationTests {

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private ProductListRepository productListRepository;
    @MockBean
    private  UserService userService;

    @MockBean
    private ProductService productService;

    @MockBean
    private ProductListService productListService;

    @Autowired
    private  MockMvc mockMvc;

    @MockBean
    private JwtTokenGenerator jwtTokenGenerator;

    @MockBean
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private JwtTokenVerifier tokenVerifier;

    @MockBean
    private AuthenticationConfiguration authenticationConfiguration;

    @MockBean
    private EmailAndPasswordAuthenticationFilter emailAndPasswordAuthenticationFilter;

    @MockBean
    private GlobalSecurityConfiguration configuration;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private CommandLineRunner commandLineRunner;
    @Test
    public void registrationOkTest() throws Exception {
        given(userService.buildUser(userDto,password)).willReturn(user);
        var TOKEN_ATTR_NAME = "org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository.CSRF_TOKEN";
        var httpSessionCsrfTokenRepository = new HttpSessionCsrfTokenRepository();
        var csrfToken = httpSessionCsrfTokenRepository.generateToken(new MockHttpServletRequest());
        MvcResult registration = mockMvc.perform(post("/tiwo/user/register?password="+password)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept("*/*")
                        .param("action", "signup")
                        .sessionAttr(TOKEN_ATTR_NAME, csrfToken)
                        .param(csrfToken.getParameterName(), csrfToken.getToken())
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().is(201))
                .andReturn();
    }

    @Test
    public void registrationUserAlreadyExistsTest() throws Exception {
        given(userService.buildUser(userDto,password)).willReturn(user);
        given(userService.addUser(any())).willThrow(UserAlreadyExistsException.class);
        var TOKEN_ATTR_NAME = "org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository.CSRF_TOKEN";
        var httpSessionCsrfTokenRepository = new HttpSessionCsrfTokenRepository();
        var csrfToken = httpSessionCsrfTokenRepository.generateToken(new MockHttpServletRequest());
        MvcResult registration = mockMvc.perform(post("/tiwo/user/register?password="+password)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept("*/*")
                        .param("action", "signup")
                        .sessionAttr(TOKEN_ATTR_NAME, csrfToken)
                        .param(csrfToken.getParameterName(), csrfToken.getToken())
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().is(409))
                .andReturn();
    }

    @Test
    public void loginOkTest() throws Exception {
        given(userService.getUserByEmail(loginDto.getEmail())).willReturn(user);
        var TOKEN_ATTR_NAME = "org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository.CSRF_TOKEN";
        var httpSessionCsrfTokenRepository = new HttpSessionCsrfTokenRepository();
        var csrfToken = httpSessionCsrfTokenRepository.generateToken(new MockHttpServletRequest());
        MvcResult login = mockMvc.perform(post("/tiwo/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept("*/*")
                        .param("action", "signup")
                        .sessionAttr(TOKEN_ATTR_NAME, csrfToken)
                        .param(csrfToken.getParameterName(), csrfToken.getToken())
                        .content(objectMapper.writeValueAsString(loginDto)))
                .andExpect(status().is(200))
                .andReturn();
    }

    @Test
    public void loginNoSuchUserTest() throws Exception {
        given(userService.getUserByEmail(loginDto.getEmail())).willReturn(null);
        var TOKEN_ATTR_NAME = "org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository.CSRF_TOKEN";
        var httpSessionCsrfTokenRepository = new HttpSessionCsrfTokenRepository();
        var csrfToken = httpSessionCsrfTokenRepository.generateToken(new MockHttpServletRequest());
        MvcResult login = mockMvc.perform(post("/tiwo/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept("*/*")
                        .param("action", "signup")
                        .sessionAttr(TOKEN_ATTR_NAME, csrfToken)
                        .param(csrfToken.getParameterName(), csrfToken.getToken())
                        .content(objectMapper.writeValueAsString(loginDto)))
                .andExpect(status().is(404))
                .andReturn();
    }

    @Test
    public void loginWrongPasswordTest() throws Exception {
        given(userService.getUserByEmail(loginDto1.getEmail())).willReturn(user);
        var TOKEN_ATTR_NAME = "org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository.CSRF_TOKEN";
        var httpSessionCsrfTokenRepository = new HttpSessionCsrfTokenRepository();
        var csrfToken = httpSessionCsrfTokenRepository.generateToken(new MockHttpServletRequest());
        MvcResult login = mockMvc.perform(post("/tiwo/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept("*/*")
                        .param("action", "signup")
                        .sessionAttr(TOKEN_ATTR_NAME, csrfToken)
                        .param(csrfToken.getParameterName(), csrfToken.getToken())
                        .content(objectMapper.writeValueAsString(loginDto1)))
                .andExpect(status().is(400))
                .andReturn();
    }

    @Test
    public void getListOkTest() throws Exception {
        given(userService.getUserByEmail("testEmail")).willReturn(user);
        var TOKEN_ATTR_NAME = "org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository.CSRF_TOKEN";
        var httpSessionCsrfTokenRepository = new HttpSessionCsrfTokenRepository();
        var csrfToken = httpSessionCsrfTokenRepository.generateToken(new MockHttpServletRequest());
        MvcResult login = mockMvc.perform(get("/tiwo/user/getLists?email="+"testEmail")
                        .accept("*/*")
                        .param("action", "signup")
                        .sessionAttr(TOKEN_ATTR_NAME, csrfToken)
                        .param(csrfToken.getParameterName(), csrfToken.getToken()))
                .andExpect(status().is(200))
                .andReturn();
    }
}
