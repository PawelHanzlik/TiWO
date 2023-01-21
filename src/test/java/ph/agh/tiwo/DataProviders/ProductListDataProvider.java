package ph.agh.tiwo.DataProviders;

import ph.agh.tiwo.dto.ProductListDto;
import ph.agh.tiwo.entity.Product;
import ph.agh.tiwo.entity.ProductList;
import ph.agh.tiwo.entity.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
    public static List<Product> products1;

    public static ProductListDto productListDto;

    public static ProductList productListInt;
    public static ProductList productListInt1;

    public static ProductListDto productListDtoInt;
    static {
        user = User.builder().id(1L).name("test").surname("test").build();
        products = Arrays.asList(Product.builder().id(10L).name("test_1").cost(5.0).build(),
                Product.builder().id(11L).name("test_2").cost(15.0).bought(true).quantity(2.0).type("sztuk").build());
        products1 = Arrays.asList(Product.builder().id(10L).name("test_1").cost(5.0).build(),
                Product.builder().id(11L).name("test_2").cost(15.0).build(),
                Product.builder().id(12L).name("test_2").cost(15.0).build());
        productList = ProductList.builder().id(productListId).name("test_1")
                .products(products).description("test").dueTo(LocalDate.now()).build();
        productList1 = new ProductList();
        productList1.setId(2L);
        productList1.setName("test_2");
        productList1.setProducts(products);
        productList1.setDescription("test_2");

        productListDto = new ProductListDto("test_3",products1,"test_2",LocalDate.now());
        productListDto.setDescription("test_3");
        productList2 = ProductList.builder().id(productListId).name("test_3")
                .products(products1).description("test_3").build();
        productList3 = ProductList.builder().id(productListId).name("test_1")
                .products(products).description("test").build();
        productLists = new ArrayList<>();
        productLists.add(productList);
        productLists.add(productList1);

        productListInt = ProductList.builder().id(1L).name("testInt_1").description("testInt_1").build();
        productListInt1 = ProductList.builder().id(2L).name("testInt_2").description("testInt_2").build();

        productListDtoInt = new ProductListDto("testInt_3", Collections.emptyList(),"testInt_3",LocalDate.now());
    }
}
