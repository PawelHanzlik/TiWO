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
import ph.agh.tiwo.entity.ProductList;
import ph.agh.tiwo.entity.User;
import ph.agh.tiwo.repository.UserRepository;
import ph.agh.tiwo.service.ProductListService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ph.agh.tiwo.DataProviders.ProductListIntegrationTestsDataProvider.productListDto;

@SpringBootTest
@AutoConfigureMockMvc()
public class ProductListControllerIntegrationTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductListService productListService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    String token;

    @Before
    public void addEntities(){
        this.userRepository.save(User.builder().email("testEmail").password("testPassword").name("test").surname("test").build());
        this.productListService.addProductList(ProductList.builder().name("test").description("test").build());
        this.productListService.addProductList(ProductList.builder().name("test2").description("test").build());
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
    public void addProductListOkTest_UserLogged() throws Exception {
        this.userRepository.save(User.builder().email("test").surname("test").password("test").name("test").money(100.0).build());
        mockMvc.perform(post("/tiwo/productList/addProductList?userEmail="+"test")
                        .accept("*/*")
                        .param("action", "signup")
                        .header(HttpHeaders.AUTHORIZATION,token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productListDto)))
                .andExpect(status().is(201))
                .andReturn();
    }

    @Test
    public void addProductListOkTest_UserNotLogged() throws Exception {
        mockMvc.perform(post("/tiwo/productList/addProductList?userEmail="+"test")
                        .accept("*/*")
                        .param("action", "signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productListDto)))
                .andExpect(status().is(401))
                .andReturn();
    }

    @Test
    public void addProductListTestNoProductList_UserLogged() throws Exception {
        mockMvc.perform(post("/tiwo/productList/addProductList?userEmail="+"notExisting")
                        .accept("*/*")
                        .param("action", "signup")
                        .header(HttpHeaders.AUTHORIZATION,token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productListDto)))
                .andExpect(status().is(404))
                .andReturn();
    }
    @Test
    public void updateProductListOkTest_UserLogged() throws Exception {
        Long id = this.productListService.getAllProductLists().get(0).getId();
        mockMvc.perform(put("/tiwo/productList/updateProductList?listId="+id)
                        .accept("*/*")
                        .param("action", "signup")
                        .header(HttpHeaders.AUTHORIZATION,token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productListDto)))
                .andExpect(status().is(200))
                .andReturn();
    }

    @Test
    public void updateProductListOkTest_UserNotLogged() throws Exception {
        Long id = this.productListService.getAllProductLists().get(0).getId();
        mockMvc.perform(put("/tiwo/productList/updateProductList?listId="+id)
                        .accept("*/*")
                        .param("action", "signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productListDto)))
                .andExpect(status().is(401))
                .andReturn();
    }

    @Test
    public void updateProductListTestNoProductList_UserLogged() throws Exception {
        mockMvc.perform(put("/tiwo/productList/updateProductList?listId="+"999")
                        .accept("*/*")
                        .param("action", "signup")
                        .header(HttpHeaders.AUTHORIZATION,token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productListDto)))
                .andExpect(status().is(404))
                .andReturn();
    }

    @Test
    public void deleteProductListOkTest_UserLogged() throws Exception {
        Long id = this.productListService.getAllProductLists().get(0).getId();
        mockMvc.perform(delete("/tiwo/productList/deleteProductList?listId="+id)
                        .accept("*/*")
                        .header(HttpHeaders.AUTHORIZATION,token)
                        .param("action", "signup"))
                .andExpect(status().is(200))
                .andReturn();
    }

    @Test
    public void deleteProductListTest_UserNotLogged() throws Exception {
        Long id = this.productListService.getAllProductLists().get(0).getId();
        mockMvc.perform(delete("/tiwo/productList/deleteProductList?listId="+id)
                        .accept("*/*")
                        .param("action", "signup"))
                .andExpect(status().is(401))
                .andReturn();
    }

    @Test
    public void deleteProductListTestNoProductList_UserLogged() throws Exception {
        mockMvc.perform(delete("/tiwo/productList/deleteProductList?listId="+"999")
                        .accept("*/*")
                        .header(HttpHeaders.AUTHORIZATION,token)
                        .param("action", "signup"))
                .andExpect(status().is(404))
                .andReturn();
    }
}
