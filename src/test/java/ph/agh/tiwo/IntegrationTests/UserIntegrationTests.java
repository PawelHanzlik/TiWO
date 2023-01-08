package ph.agh.tiwo.IntegrationTests;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ph.agh.tiwo.entity.User;
import ph.agh.tiwo.exception.Classes.NoSuchUserException;
import ph.agh.tiwo.repository.ProductListRepository;
import ph.agh.tiwo.repository.UserRepository;
import ph.agh.tiwo.service.UserService;

import static org.junit.jupiter.api.Assertions.*;
import static ph.agh.tiwo.DataProviders.UserServiceDataProvider.*;
@SpringBootTest
public class UserIntegrationTests {


    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProductListRepository productListRepository;

    @BeforeEach
    void setUp() {
        this.productListRepository.saveAll(productListsInt);
        this.userRepository.saveAll(usersInt);
    }

    @AfterEach
    void cleanUp() {
        this.userRepository.deleteAll();
    }
    @Test
    public void getUserOkTest(){
        System.out.println(this.userService.getAllUsers());
        User user = this.userService.getUserById(2L);
        assertNotNull(user);
        assertEquals(2L, user.getId());
        assertEquals("test_name",user.getName());
        assertEquals("test_surname",user.getSurname());
        assertEquals("test_email",user.getEmail());
        assertEquals("test_pass",user.getPassword());
    }

    @Test
    public void getNotExistingUserTest(){
        assertThrows(NoSuchUserException.class,
                () -> this.userService.getUserById(999L));

    }
}
