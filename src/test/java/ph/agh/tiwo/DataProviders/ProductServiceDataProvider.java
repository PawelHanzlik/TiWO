package ph.agh.tiwo.DataProviders;

import ph.agh.tiwo.entity.Product;
import ph.agh.tiwo.entity.ProductList;

import java.util.ArrayList;
import java.util.List;

public class ProductServiceDataProvider {

    public static final Long productId = (long) 1;
    public static Product product;
    public static Product product1;
    public static Product product2;
    public static Product product3;
    public static List<Product> products;
    public static ProductList productList;
    public static ProductList productList1;

    static {
        productList = ProductList.builder().id(2L).name("test_1").description("test").build();
        productList1 = ProductList.builder().id(3L).name("test_2").description("test_2").build();
        product = Product.builder().id(productId).name("test_1").cost(5.0).productList(productList).build();
        product1 = Product.builder().id(productId).name("test_2").cost(10.0).productList(productList).build();
        product2 = Product.builder().id(productId).name("test_3").cost(15.0).productList(productList).build();
        product3 = new Product();
        product3.setId(productId);
        product3.setName("test_4");
        product3.setCost(20.0);
        product3.setProductList(productList1);
        products = new ArrayList<>();
        products.add(product2);
        products.add(product3);
    }
}
