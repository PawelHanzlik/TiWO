package ph.agh.tiwo.DataProviders;


import ph.agh.tiwo.dto.UserDto;
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
    public static User user3;
    public static List<User> users;
    public static ProductList productList;
    public static ProductList productList1;
    public static Set<ProductList> productLists;
    public static Set<ProductList> productLists1;

    public static UserDto userDto;
    public static UserDto userDto1;

    public static User userInt;
    public static User userInt1;
    public static List<User> usersInt;
    public static Set<ProductList> productListsInt;
    public static ProductList productListInt;

    static {
        productList = ProductList.builder().id(2L).name("test_1").build();
        productList1 = ProductList.builder().id(3L).name("test_1").build();
        productLists = new HashSet<>();
        productLists.add(productList);
        productLists1 = new HashSet<>();
        productLists1.add(productList);
        productLists1.add(productList1);
        user = User.builder().id(userId).name("test_name").surname("test_surname").email("test_email").password("test_pass").productLists(productLists).build();
        user1 = User.builder().id(3L).name("new_name").surname("new_surname").email("new_email").password("new_pass").productLists(productLists).build();
        user2 = new User();
        user2.setId(userId);
        user2.setName("test_name_1");
        user2.setSurname("test_surname_1");
        user2.setEmail("test_email_1");
        user2.setPassword("test_pass");
        user2.setProductLists(productLists1);
        users = new ArrayList<>();
        users.add(user);
        users.add(user1);

        user3 = User.builder().id(userId).name("test_name").surname("test_surname").email("test_email").password("test_pass").productLists(productLists).build();
        userDto = new UserDto("test_name_1","test_surname","test_email_1",100.0, productLists1);
        userDto.setSurname("test_surname_1");
        userDto1 = new UserDto();
        userDto1.setEmail("email");
        userDto1.setSurname("surname");
        userDto1.setName("name");
        userDto.setProductLists(productLists);

        userInt = User.builder().id(2L).name("test_name").surname("test_surname").email("test_email").password("test_pass").money(100.0).productLists(productListsInt).build();
        userInt1 = User.builder().id(3L).name("new_name").surname("new_surname").email("new_email").password("new_pass").money(100.0).productLists(productListsInt).build();
        productListInt = ProductList.builder().id(2L).name("test_1").description("test").build();
        productListsInt = new HashSet<>();
        productListsInt.add(productListInt);
        usersInt = new ArrayList<>();
        usersInt.add(userInt);
        usersInt.add(userInt1);
    }
}
