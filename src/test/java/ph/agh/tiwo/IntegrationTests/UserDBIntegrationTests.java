package ph.agh.tiwo.IntegrationTests;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ph.agh.tiwo.entity.User;
import ph.agh.tiwo.exception.Classes.NoSuchUserException;
import ph.agh.tiwo.exception.Classes.UserAlreadyExistsException;
import ph.agh.tiwo.repository.ProductListRepository;
import ph.agh.tiwo.repository.UserRepository;
import ph.agh.tiwo.service.UserService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static ph.agh.tiwo.DataProviders.UserServiceDataProvider.*;
@SpringBootTest
public class UserDBIntegrationTests {


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
    public void getUserByEmailOkTest(){
        User user = this.userService.getUserByEmail("test_email");
        assertNotNull(user);
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
    @Test
    public void addUserThenGetUserOkTest(){
        User userIntNew = User.builder().email("email").password("pass").name("name").surname("surname").money(100.0).build();
        this.userService.addUser(userIntNew);
        User user = this.userService.getUserByEmail("email");
        assertNotNull(user);
        assertEquals("name",user.getName());
        assertEquals("surname",user.getSurname());
        assertEquals("email",user.getEmail());
        assertEquals("pass",user.getPassword());
    }

    @Test
    public void addUserAlreadyExistingUserTest(){
        assertThrows(UserAlreadyExistsException.class,
                () -> this.userService.addUser(userInt1));

    }

    @Test
    void updateUserTest(){
        Optional<User> optionalUser = this.userRepository.findByEmail("test_name");
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            User updatedUser = this.userService.updateUser(user.getId(), userDto);
            assertNotNull(updatedUser);
            assertEquals(userId, updatedUser.getId());
            assertEquals("test_name_1", updatedUser.getName());
            assertEquals("test_surname_1", updatedUser.getSurname());
            assertEquals("test_email_1", updatedUser.getEmail());
            assertEquals("test_pass", updatedUser.getPassword());
            assertEquals(productLists1, updatedUser.getProductLists());
        }
    }

    @Test
    void updateUserNoSuchUserExceptionTest(){
        assertThrows(NoSuchUserException.class, () -> userService.updateUser(999L,userDto));
    }

    @Test
    void deleteUserTest() {
        Optional<User> optionalUser = this.userRepository.findByEmail("test_name");
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            userService.deleteUser(user.getId());

            assertThrows(NoSuchUserException.class,
                    () -> this.userService.getUserById(user.getId()));
        }
    }

    @Test
    void deleteUserNoSuchUserExceptionTest(){
        assertThrows(NoSuchUserException.class, () -> userService.deleteUser(999L));
    }
}
