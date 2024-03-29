package ph.agh.tiwo.UnitTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import ph.agh.tiwo.entity.User;
import ph.agh.tiwo.exception.Classes.NoSuchUserException;
import ph.agh.tiwo.exception.Classes.UserAlreadyExistsException;
import ph.agh.tiwo.repository.UserRepository;
import ph.agh.tiwo.service.UserService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static ph.agh.tiwo.DataProviders.UserServiceDataProvider.*;

public class UserServiceUnitTests {

    @InjectMocks
    UserService userService;

    @Mock
    UserRepository userRepository;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getUserByIdTest(){
        when(userRepository.findById(userId)).thenReturn(Optional.ofNullable(user));
        User getUser = this.userService.getUserById(userId);
        assertNotNull(getUser);
        System.out.println(getUser);
        assertEquals(1, getUser.getId());
        assertEquals("test_name",getUser.getName());
        assertEquals("test_surname",getUser.getSurname());
        assertEquals("test_email",getUser.getEmail());
        assertEquals("test_pass",getUser.getPassword());
        assertEquals(productLists, getUser.getProductLists());
    }

    @Test
    void getUserByIdNoSuchUserExceptionTest(){
        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        assertThrows(NoSuchUserException.class, () -> userService.getUserById(userId));
    }

    @Test
    void getUserByEmailTest(){
        when(userRepository.findByEmail("test_email")).thenReturn(Optional.ofNullable(user));
        User getUser = this.userService.getUserByEmail("test_email");
        assertNotNull(getUser);
        System.out.println(getUser);
        assertEquals(1, getUser.getId());
        assertEquals("test_name",getUser.getName());
        assertEquals("test_surname",getUser.getSurname());
        assertEquals("test_email",getUser.getEmail());
        assertEquals("test_pass",getUser.getPassword());
        assertEquals(productLists, getUser.getProductLists());
    }

    @Test
    void getUserByEmailNoSuchUserExceptionTest(){
        when(userRepository.findByEmail("test_email")).thenReturn(Optional.empty());
        assertThrows(NoSuchUserException.class, () -> userService.getUserByEmail("test_email"));
    }

    @Test
    void getAllUsers(){
        when(userRepository.findAll()).thenReturn(users);
        List<User> usersList = this.userService.getAllUsers();
        assertEquals(user,usersList.get(0));
        assertEquals(user1,usersList.get(1));
    }
    @Test
    void addUserTest(){
        when(userRepository.save(any(User.class))).thenReturn(user1);
        User newUser = this.userService.addUser(user1);
        assertNotNull(newUser);
        assertEquals(3, newUser.getId());
        assertEquals("new_name",newUser.getName());
        assertEquals("new_surname",newUser.getSurname());
        assertEquals("new_email",newUser.getEmail());
        assertEquals("new_pass",newUser.getPassword());
        assertEquals(productLists, newUser.getProductLists());
    }

    @Test
    void addUserUserAlreadyExistsExceptionTest(){
        when(userRepository.findByEmail("test_email")).thenReturn(Optional.ofNullable(user));
        assertThrows(UserAlreadyExistsException.class, () -> this.userService.addUser(user));
    }

    @Test
    void updateUserByIdTest(){
        when(userRepository.findById(userId)).thenReturn(Optional.ofNullable(user3));
        when(userRepository.save(any(User.class))).thenReturn(user2);
        User updatedUser = this.userService.updateUser(userId,userDto);
        assertNotNull(updatedUser);
        assertEquals(userId, updatedUser.getId());
        assertEquals("test_name_1",updatedUser.getName());
        assertEquals("test_surname_1",updatedUser.getSurname());
        assertEquals("test_email_1",updatedUser.getEmail());
        assertEquals("test_pass",updatedUser.getPassword());
        assertEquals(productLists1, updatedUser.getProductLists());
    }

    @Test
    void updateUserByEmailTest(){
        when(userRepository.findByEmail(user3.getEmail())).thenReturn(Optional.ofNullable(user3));
        when(userRepository.save(any(User.class))).thenReturn(user2);
        User updatedUser = this.userService.updateUser(user3.getEmail(),userDto);
        assertNotNull(updatedUser);
        assertEquals(userId, updatedUser.getId());
        assertEquals("test_name_1",updatedUser.getName());
        assertEquals("test_surname_1",updatedUser.getSurname());
        assertEquals("test_email_1",updatedUser.getEmail());
        assertEquals("test_pass",updatedUser.getPassword());
        assertEquals(productLists1, updatedUser.getProductLists());
    }

    @Test
    void updateUserNoSuchUserExceptionTest(){
        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        assertThrows(NoSuchUserException.class, () -> userService.updateUser(userId,userDto));
    }

    @Test
    void updateUserByEmailNoSuchUserExceptionTest(){
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());
        assertThrows(NoSuchUserException.class, () -> userService.updateUser(user.getEmail(),userDto));
    }

    @Test
    void deleteUserTest() {
        Optional<User> optionalUser = Optional.of(user);

        when(userRepository.findById(userId)).thenReturn(optionalUser);

        userService.deleteUser(userId);

        Mockito.verify(userRepository, times(1)).delete(optionalUser.get());
    }

    @Test
    void deleteUserNoSuchUserExceptionTest(){
        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        assertThrows(NoSuchUserException.class, () -> userService.deleteUser(userId));
    }

    @Test
    void buildUserTest(){
        User user = this.userService.buildUser(userDto1,"pass");
        assertNotNull(user);
        assertEquals("name",user.getName());
        assertEquals("surname",user.getSurname());
        assertEquals("email",user.getEmail());
        assertEquals("pass",user.getPassword());
        assertEquals(Collections.emptySet(), user.getProductLists());
    }
}
