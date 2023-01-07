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
    public static ProductList productList2;
    public static ProductList productList3;
    public static List<ProductList> productLists;

    public static User user ;

    public static List<Product> products;
    static {
        user = User.builder().id(1L).name("test").surname("test").build();
        products = Arrays.asList(Product.builder().id(10L).name("test_1").cost(5.0).build(),
                Product.builder().id(11L).name("test_2").cost(15.0).build());
        productList = ProductList.builder().id(productListId).name("test_1").user(user)
                .products(products).description("test").build();
        productList1 = ProductList.builder().id(2L).name("test_2").user(user)
                .products(products).description("test_2").build();
//        product1 = Product.builder().id(productId).name("test_2").cost(10.0).build();
//        product2 = Product.builder().id(productId).name("test_3").cost(15.0).build();
//        product3 = Product.builder().id(productId).name("test_4").cost(20.0).build();
        productLists = new ArrayList<>();
        productLists.add(productList);
        productLists.add(productList1);
    }
}
