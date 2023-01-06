package ph.agh.tiwo.DataProviders;


import ph.agh.tiwo.entity.User;

import java.util.ArrayList;
import java.util.List;

public class UserServiceDataProvider {

    public static final Long userId = (long)1;
    public static User user;
    public static User user1;
    public static User user2;
    public static List<User> users;
    static {
        user = User.builder().id(userId).name("test_name").surname("test_surname").email("test_email").password("test_pass").build();
        user1 = User.builder().id(userId).name("new_name").surname("new_surname").email("new_email").password("new_pass").build();
        user2 = User.builder().id(userId).name("test_name1").surname("test_surname1").email("test_email1").password("test_pass1").build();
        users = new ArrayList<>();
        users.add(user);
        users.add(user1);
    }
}
