package ph.agh.tiwo.IntegrationTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ph.agh.tiwo.dto.LoginDto;
import ph.agh.tiwo.entity.Product;
import ph.agh.tiwo.entity.ProductList;
import ph.agh.tiwo.entity.User;
import ph.agh.tiwo.repository.ProductListRepository;
import ph.agh.tiwo.repository.UserRepository;
import ph.agh.tiwo.service.ProductService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ph.agh.tiwo.DataProviders.ProductIntegrationTestsDataProvider.product;
import static ph.agh.tiwo.DataProviders.ProductIntegrationTestsDataProvider.productDto;

@SpringBootTest
@AutoConfigureMockMvc()
public class ProductControllerIntegrationTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ProductListRepository productListRepository;

    @Autowired
    ProductService productService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    UserRepository userRepository;

    String token;

    @Before
    public void addEntities(){
        this.userRepository.save(User.builder().email("testEmail").password("testPassword").name("test").surname("test").build());
        this.productListRepository.save(ProductList.builder().name("test").build());
        this.productService.addProduct(Product.builder().name("test").type("test").quantity(1D).build());
        this.productService.addProduct(Product.builder().name("test2").type("test2").quantity(1D).build());
    }
    @BeforeEach
    public void generateToken() throws Exception {
        LoginDto loginDto = new LoginDto("testEmail", "testPassword");

        MvcResult login = mockMvc.perform(post("/tiwo/user/login")
                        .content(objectMapper.writeValueAsString(loginDto)))
                .andExpect(status().is(200))
                .andReturn();

        String response = login.getResponse().getContentAsString();
        token = response.substring(response.indexOf("Bearer"), response.length() - 2);
    }
    @Test
    public void addProductOkTest_UserLogged() throws Exception {
        String name = this.productListRepository.findAll().get(0).getName();
        mockMvc.perform(post("/tiwo/product/addProduct?listName="+name)
                        .accept("*/*")
                        .param("action", "signup")
                        .header(HttpHeaders.AUTHORIZATION,token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDto)))
                .andExpect(status().is(201))
                .andReturn();
    }

    @Test
    public void addProductOkTest_UserNotLogged() throws Exception {
        String name = this.productListRepository.findAll().get(0).getName();
        mockMvc.perform(post("/tiwo/product/addProduct?listName="+name)
                        .accept("*/*")
                        .param("action", "signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDto)))
                .andExpect(status().is(401))
                .andReturn();
    }

    @Test
    public void addProductNoProductListTest() throws Exception {
        mockMvc.perform(post("/tiwo/product/addProduct?listName="+"nonExisting")
                        .accept("*/*")
                        .param("action", "signup")
                        .header(HttpHeaders.AUTHORIZATION,token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDto)))
                .andExpect(status().is(404))
                .andReturn();
    }

    @Test
    public void updateProductOkTest_UserLogged() throws Exception {
        Long id = this.productService.getAllProducts().get(0).getId();
        mockMvc.perform(put("/tiwo/product/updateProduct?productId="+id)
                        .accept("*/*")
                        .param("action", "signup")
                        .header(HttpHeaders.AUTHORIZATION,token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().is(200))
                .andReturn();
    }

    @Test
    public void updateProductOkTest_UserNotLogged() throws Exception {
        Long id = this.productService.getAllProducts().get(0).getId();
        mockMvc.perform(put("/tiwo/product/updateProduct?productId="+id)
                        .accept("*/*")
                        .param("action", "signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().is(401))
                .andReturn();
    }

    @Test
    public void updateProductTestNoProduct_UserLogged() throws Exception {
        this.productService.addProduct(Product.builder().id(2L).name("test").quantity(1.0).type("test").build());
        mockMvc.perform(put("/tiwo/product/updateProduct?productId="+"999")
                        .accept("*/*")
                        .param("action", "signup")
                        .header(HttpHeaders.AUTHORIZATION,token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().is(404))
                .andReturn();
    }

    @Test
    public void updateProductBoughtOkTest_UserLogged() throws Exception {
        Long id = this.productService.getAllProducts().get(0).getId();
        mockMvc.perform(put("/tiwo/product/updateProductBought?productId="+id)
                        .accept("*/*")
                        .param("action", "signup")
                        .header(HttpHeaders.AUTHORIZATION,token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().is(200))
                .andReturn();
    }

    @Test
    public void deleteProductOkTest_UserLogged() throws Exception {
        Long id = this.productService.getAllProducts().get(0).getId();
        mockMvc.perform(delete("/tiwo/product/deleteProduct?productId="+id)
                        .accept("*/*")
                        .param("action", "signup")
                        .header(HttpHeaders.AUTHORIZATION,token))
                .andExpect(status().is(200))
                .andReturn();
    }

    @Test
    public void deleteProductOkTest_UserNotLogged() throws Exception {
        Long id = this.productService.getAllProducts().get(0).getId();
        mockMvc.perform(delete("/tiwo/product/deleteProduct?productId="+id)
                        .accept("*/*")
                        .param("action", "signup"))
                .andExpect(status().is(401))
                .andReturn();
    }

    @Test
    public void deleteProductTestNoProduct_UserLogged() throws Exception {
        mockMvc.perform(delete("/tiwo/product/deleteProduct?productId="+"999")
                        .accept("*/*")
                        .param("action", "signup")
                        .header(HttpHeaders.AUTHORIZATION,token))
                .andExpect(status().is(404))
                .andReturn();
    }
}
