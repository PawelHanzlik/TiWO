package ph.agh.tiwo.DataProviders;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ph.agh.tiwo.dto.LoginDto;
import ph.agh.tiwo.dto.UserDto;
import ph.agh.tiwo.entity.User;

import java.util.Collections;

public class UserIntegrationTestsDataProvider {

    public  static LoginDto loginDto;
    public  static LoginDto loginDto1;
    public static UserDto userDto;
    public static String password;

    public static User user;

    public static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static String passwordEncrypted = passwordEncoder.encode("testPassword");

    static {
        loginDto = new LoginDto("testEmail", passwordEncrypted);
        loginDto1 = new LoginDto("testEmail", "wrongPassword");
        userDto = new UserDto("testName", "testSurname","testEmail", Collections.emptySet());
        password = "testPassword";
        user = User.builder().name("testName").surname("testSurname").email("testEmail")
                .productLists(Collections.emptySet()).password(passwordEncrypted).build();
    }
}
