package ph.agh.tiwo.DataProviders;


import ph.agh.tiwo.entity.ProductList;
import ph.agh.tiwo.entity.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserServiceDataProvider {

    public static final Long userId = (long)1;
    public static User user;
    public static User user1;
    public static User user2;
    public static List<User> users;
    public static ProductList productList;
    public static Set<ProductList> productLists;
    static {
        productList = ProductList.builder().id(2L).name("test_1").user(user).build();
        productLists = new HashSet<>();
        productLists.add(productList);
        user = User.builder().id(userId).name("test_name").surname("test_surname").email("test_email").password("test_pass").productLists(productLists).build();
        user1 = User.builder().id(userId).name("new_name").surname("new_surname").email("new_email").password("new_pass").productLists(productLists).build();
        user2 = new User();
        user2.setId(userId);
        user2.setName("test_name1");
        user2.setSurname("test_surname1");
        user2.setEmail("test_email1");
        user2.setPassword("test_pass1");
        users = new ArrayList<>();
        users.add(user);
        users.add(user1);
    }
}
