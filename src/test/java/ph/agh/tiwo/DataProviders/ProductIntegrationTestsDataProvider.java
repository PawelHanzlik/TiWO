package ph.agh.tiwo.DataProviders;

import ph.agh.tiwo.dto.ProductDto;
import ph.agh.tiwo.entity.Product;
import ph.agh.tiwo.entity.ProductList;

import java.util.ArrayList;
import java.util.List;

public class ProductIntegrationTestsDataProvider {

    public static final Long productId = (long) 1;
    public static Product product;
    public static Product product1;
    public static Product product2;
    public static Product product3;
    public static List<Product> products;
    public static ProductList productList;
    public static ProductList productList1;

    public static Product productInt;
    public static Product productInt1;
    public static ProductDto productDto;
    public static ProductDto productDto1;
    public static Product productInt2;

    static {
        productList = ProductList.builder().id(2L).name("test_1").description("test").build();
        productList1 = ProductList.builder().id(3L).name("test_2").description("test_2").build();
        product = Product.builder().id(productId).name("test_1").cost(5.0)
                .bought(true).quantity(2.0).type("sztuk").build();
        product1 = Product.builder().id(productId).name("test_2").cost(10.0).build();
        product2 = Product.builder().id(productId).name("test_3").cost(15.0).build();
        product3 = new Product();
        product3.setId(productId);
        product3.setName("test_4");
        product3.setCost(20.0);
        products = new ArrayList<>();
        products.add(product2);
        products.add(product3);

        productInt = Product.builder().id(100L).name("testInt_1").cost(5.0).build();
        productInt1 = Product.builder().id(200L).name("testInt_2").cost(10.0).build();
        productDto = new ProductDto("test", 2.3,"test");
        productDto1 = ProductDto.builder().name("name").quantity(1.0).type("test").build();
        productInt2 = Product.builder().name("test").quantity(2.3).type("test").productList(productList).build();
    }
}
