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
import ph.agh.tiwo.controller.ProductListController;
import ph.agh.tiwo.dto.ProductListDto;
import ph.agh.tiwo.entity.User;
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

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ph.agh.tiwo.DataProviders.ProductListIntegrationTestsDataProvider.*;

@WebMvcTest(ProductListController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ProductListControllerIntegrationTests {

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private ProductListRepository productListRepository;
    @MockBean
    private UserService userService;

    @MockBean
    private ProductService productService;

    @MockBean
    private ProductListService productListService;

    @Autowired
    private MockMvc mockMvc;

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
    public void addProductListOkTest() throws Exception {
        given(userRepository.findByEmail("test")).willReturn(Optional.ofNullable(User.builder().email("test").build()));
        given(productListService.buildProductList(new ProductListDto(),User.builder().email("test").build()))
                .willReturn(productList);
        given(productListService.addProductList(productList)).willReturn(productList1);
        var TOKEN_ATTR_NAME = "org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository.CSRF_TOKEN";
        var httpSessionCsrfTokenRepository = new HttpSessionCsrfTokenRepository();
        var csrfToken = httpSessionCsrfTokenRepository.generateToken(new MockHttpServletRequest());
        MvcResult addProduct = mockMvc.perform(post("/tiwo/productList/addProductList?userEmail="+"test")
                        .accept("*/*")
                        .param("action", "signup")
                        .sessionAttr(TOKEN_ATTR_NAME, csrfToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param(csrfToken.getParameterName(), csrfToken.getToken())
                        .content(objectMapper.writeValueAsString(productListDto)))
                .andExpect(status().is(201))
                .andReturn();
    }

    @Test
    public void addProductListNoUserTest() throws Exception {
        given(userRepository.findByEmail("test")).willReturn(Optional.empty());
        given(productListService.buildProductList(new ProductListDto(),User.builder().email("test").build()))
                .willReturn(productList);
        given(productListService.addProductList(productList)).willReturn(productList1);
        var TOKEN_ATTR_NAME = "org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository.CSRF_TOKEN";
        var httpSessionCsrfTokenRepository = new HttpSessionCsrfTokenRepository();
        var csrfToken = httpSessionCsrfTokenRepository.generateToken(new MockHttpServletRequest());
        MvcResult addProduct = mockMvc.perform(post("/tiwo/productList/addProductList?userEmail="+"test")
                        .accept("*/*")
                        .param("action", "signup")
                        .sessionAttr(TOKEN_ATTR_NAME, csrfToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param(csrfToken.getParameterName(), csrfToken.getToken())
                        .content(objectMapper.writeValueAsString(productListDto)))
                .andExpect(status().is(404))
                .andReturn();
    }

    @Test
    public void updateProductListOkTest() throws Exception {
        given(productListService.updateProductList(1L,productListDto)).willReturn(productList1);
        var TOKEN_ATTR_NAME = "org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository.CSRF_TOKEN";
        var httpSessionCsrfTokenRepository = new HttpSessionCsrfTokenRepository();
        var csrfToken = httpSessionCsrfTokenRepository.generateToken(new MockHttpServletRequest());
        MvcResult addProduct = mockMvc.perform(put("/tiwo/productList/updateProductList?listId="+"1")
                        .accept("*/*")
                        .param("action", "signup")
                        .sessionAttr(TOKEN_ATTR_NAME, csrfToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param(csrfToken.getParameterName(), csrfToken.getToken())
                        .content(objectMapper.writeValueAsString(productListDto)))
                .andExpect(status().is(200))
                .andReturn();
    }

    @Test
    public void deleteProductListOkTest() throws Exception {
        var TOKEN_ATTR_NAME = "org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository.CSRF_TOKEN";
        var httpSessionCsrfTokenRepository = new HttpSessionCsrfTokenRepository();
        var csrfToken = httpSessionCsrfTokenRepository.generateToken(new MockHttpServletRequest());
        MvcResult addProduct = mockMvc.perform(delete("/tiwo/productList/deleteProductList?listId="+"1")
                        .accept("*/*")
                        .param("action", "signup")
                        .sessionAttr(TOKEN_ATTR_NAME, csrfToken)
                        .param(csrfToken.getParameterName(), csrfToken.getToken()))
                .andExpect(status().is(200))
                .andReturn();
    }
}
