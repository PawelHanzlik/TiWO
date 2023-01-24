package ph.agh.tiwo.IntegrationTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ph.agh.tiwo.dto.LoginDto;
import ph.agh.tiwo.service.UserService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ph.agh.tiwo.DataProviders.UserIntegrationTestsDataProvider.*;

@SpringBootTest
@AutoConfigureMockMvc()
public class UserControllerIntegrationTests {
    @Autowired
    private  UserService userService;

    @Autowired
    private  MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;
    @Test
    public void registrationOkTest() throws Exception {
        mockMvc.perform(post("/tiwo/user/register?password="+password)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept("*/*")
                        .param("action", "signup")
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().is(201))
                .andReturn();
    }

    @Test
    public void registrationUserAlreadyExistsTest() throws Exception {
        mockMvc.perform(post("/tiwo/user/register?password="+password1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept("*/*")
                        .param("action", "signup")
                        .content(objectMapper.writeValueAsString(userDto1)))
                .andExpect(status().is(409))
                .andReturn();
    }

    @Test
    public void loginOkTest() throws Exception {
        mockMvc.perform(post("/tiwo/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept("*/*")
                        .param("action", "signup")
                        .content(objectMapper.writeValueAsString(loginDto1)))
                .andExpect(status().is(200))
                .andReturn();
    }

    @Test
    public void loginWrongPasswordTest() throws Exception {
        mockMvc.perform(post("/tiwo/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept("*/*")
                        .param("action", "signup")
                        .content(objectMapper.writeValueAsString(loginDto2)))
                .andExpect(status().is(401))
                .andReturn();
    }

    @Test
    public void getListOkTest() throws Exception {
        LoginDto loginDto = new LoginDto("testEmail", "testPassword");

        MvcResult login = mockMvc.perform(post("/tiwo/user/login")
                        .content(objectMapper.writeValueAsString(loginDto)))
                .andExpect(status().is(200))
                .andReturn();

        String response = login.getResponse().getContentAsString();
        String token = response.substring(response.indexOf("Bearer"), response.length() - 2);

        mockMvc.perform(get("/tiwo/user/getLists?email="+"testEmail")
                        .accept("*/*")
                        .header(HttpHeaders.AUTHORIZATION,token)
                        .param("action", "signup"))
                .andExpect(status().is(200))
                .andReturn();
    }
}
