package ph.agh.tiwo.DataProviders;

import ph.agh.tiwo.entity.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductServiceDataProvider {

    public static final Long productId = (long) 1;
    public static Product product;
    public static Product product1;
    public static Product product2;
    public static Product product3;
    public static List<Product> products;
    static {
        product = Product.builder().id(productId).name("test_1").cost(5.0).build();
        product1 = Product.builder().id(productId).name("test_2").cost(10.0).build();
        product2 = Product.builder().id(productId).name("test_3").cost(15.0).build();
        product3 = Product.builder().id(productId).name("test_4").cost(20.0).build();
        products = new ArrayList<>();
        products.add(product2);
        products.add(product3);
    }
}
