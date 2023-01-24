package ph.agh.tiwo.DataProviders;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ph.agh.tiwo.dto.LoginDto;
import ph.agh.tiwo.dto.UserDto;
import ph.agh.tiwo.entity.User;

import java.util.Collections;

public class UserIntegrationTestsDataProvider {

    public  static LoginDto loginDto;
    public  static LoginDto loginDto1;
    public  static LoginDto loginDto2;
    public static UserDto userDto;
    public static UserDto userDto1;
    public static String password;
    public static String password1;

    public static User user;

    public static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static String passwordEncrypted = passwordEncoder.encode("testPassword");
    public static String passwordEncrypted1 = passwordEncoder.encode("testPassword1");
    static {
        loginDto = new LoginDto("testEmail1", passwordEncrypted1);
        loginDto1 = new LoginDto("testEmail", "testPassword");
        loginDto2 = new LoginDto("testEmail", "wrongPassword");
        userDto = new UserDto("testName", "testSurname","testEmail1", Collections.emptySet());
        userDto1 = new UserDto("testName", "testSurname","testEmail", Collections.emptySet());
        password = "testPassword1";
        password1 = "testPassword";
        user = User.builder().name("testName").surname("testSurname").email("testEmail1")
                .productLists(Collections.emptySet()).password(passwordEncrypted).build();
    }
}
