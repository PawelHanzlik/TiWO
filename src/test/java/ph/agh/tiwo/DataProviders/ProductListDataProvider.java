package ph.agh.tiwo.DataProviders;

import ph.agh.tiwo.entity.Product;
import ph.agh.tiwo.entity.ProductList;
import ph.agh.tiwo.entity.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProductListDataProvider {

    public static final Long productListId = (long)1;
    public static ProductList productList;
    public static ProductList productList1;
    public static List<ProductList> productLists;

    public static User user ;

    public static List<Product> products;
    static {
        user = User.builder().id(1L).name("test").surname("test").build();
        products = Arrays.asList(Product.builder().id(10L).name("test_1").cost(5.0).build(),
                Product.builder().id(11L).name("test_2").cost(15.0).build());
        productList = ProductList.builder().id(productListId).name("test_1").user(user)
                .products(products).description("test").build();
        productList1 = new ProductList();
        productList1.setId(2L);
        productList1.setName("test_2");
        productList1.setUser(user);
        productList1.setProducts(products);
        productList1.setDescription("test_2");

        productLists = new ArrayList<>();
        productLists.add(productList);
        productLists.add(productList1);
    }
}
