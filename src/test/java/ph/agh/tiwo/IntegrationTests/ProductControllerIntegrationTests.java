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
import ph.agh.tiwo.controller.ProductController;
import ph.agh.tiwo.dto.ProductDto;
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
import static ph.agh.tiwo.DataProviders.ProductIntegrationTestsDataProvider.*;
@WebMvcTest(ProductController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ProductControllerIntegrationTests {

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
    public void addProductOkTest() throws Exception {
        given(productListRepository.findByName("test")).willReturn(Optional.ofNullable(productList));
        given(productService.buildProduct(new ProductDto("test",1.0,"test"),productList))
                .willReturn(productInt2);
        given(productService.addProduct(product)).willReturn(productInt2);
        var TOKEN_ATTR_NAME = "org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository.CSRF_TOKEN";
        var httpSessionCsrfTokenRepository = new HttpSessionCsrfTokenRepository();
        var csrfToken = httpSessionCsrfTokenRepository.generateToken(new MockHttpServletRequest());
        MvcResult addProduct = mockMvc.perform(post("/tiwo/product/addProduct?listName="+"test")
                        .accept("*/*")
                        .param("action", "signup")
                        .sessionAttr(TOKEN_ATTR_NAME, csrfToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param(csrfToken.getParameterName(), csrfToken.getToken())
                        .content(objectMapper.writeValueAsString(productDto)))
                .andExpect(status().is(201))
                .andReturn();
    }

    @Test
    public void addProductNoProductListTest() throws Exception {
        given(productListRepository.findByName("test")).willReturn(Optional.empty());
        given(productService.buildProduct(new ProductDto("test",1.0,"test"),productList))
                .willReturn(productInt2);
        given(productService.addProduct(product)).willReturn(productInt2);
        var TOKEN_ATTR_NAME = "org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository.CSRF_TOKEN";
        var httpSessionCsrfTokenRepository = new HttpSessionCsrfTokenRepository();
        var csrfToken = httpSessionCsrfTokenRepository.generateToken(new MockHttpServletRequest());
        MvcResult addProduct = mockMvc.perform(post("/tiwo/product/addProduct?listName="+"test")
                        .accept("*/*")
                        .param("action", "signup")
                        .sessionAttr(TOKEN_ATTR_NAME, csrfToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param(csrfToken.getParameterName(), csrfToken.getToken())
                        .content(objectMapper.writeValueAsString(productDto)))
                .andExpect(status().is(404))
                .andReturn();
    }

    @Test
    public void updateProductOkTest() throws Exception {
        given(productService.updateProduct(1L,product)).willReturn(productInt2);
        var TOKEN_ATTR_NAME = "org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository.CSRF_TOKEN";
        var httpSessionCsrfTokenRepository = new HttpSessionCsrfTokenRepository();
        var csrfToken = httpSessionCsrfTokenRepository.generateToken(new MockHttpServletRequest());
        MvcResult addProduct = mockMvc.perform(put("/tiwo/product/updateProduct?productId="+"1")
                        .accept("*/*")
                        .param("action", "signup")
                        .sessionAttr(TOKEN_ATTR_NAME, csrfToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param(csrfToken.getParameterName(), csrfToken.getToken())
                        .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().is(200))
                .andReturn();
    }

    @Test
    public void deleteProductOkTest() throws Exception {
        var TOKEN_ATTR_NAME = "org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository.CSRF_TOKEN";
        var httpSessionCsrfTokenRepository = new HttpSessionCsrfTokenRepository();
        var csrfToken = httpSessionCsrfTokenRepository.generateToken(new MockHttpServletRequest());
        MvcResult addProduct = mockMvc.perform(delete("/tiwo/product/deleteProduct?productId="+"1")
                        .accept("*/*")
                        .param("action", "signup")
                        .sessionAttr(TOKEN_ATTR_NAME, csrfToken)
                        .param(csrfToken.getParameterName(), csrfToken.getToken()))
                .andExpect(status().is(200))
                .andReturn();
    }
}
