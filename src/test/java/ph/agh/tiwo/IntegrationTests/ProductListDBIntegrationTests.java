package ph.agh.tiwo.IntegrationTests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ph.agh.tiwo.entity.ProductList;
import ph.agh.tiwo.exception.Classes.NoSuchProductListException;
import ph.agh.tiwo.repository.ProductListRepository;
import ph.agh.tiwo.service.ProductListService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static ph.agh.tiwo.DataProviders.ProductListDataProvider.*;


@SpringBootTest
public class ProductListDBIntegrationTests {

    @Autowired
    ProductListService productListService;

    @Autowired
    ProductListRepository productListRepository;

    @BeforeEach
    void setUp() {
        this.productListRepository.save(productListInt);
    }

    @AfterEach
    void cleanUp() {
        this.productListRepository.deleteAll();
    }

    @Test
    void getProductListTestOK(){
        ProductList getProductList = this.productListService.getProductListByName(productListInt.getName());
        assertNotNull(getProductList);
        assertEquals("testInt_1", getProductList.getName());
        assertEquals("testInt_1", getProductList.getDescription());
    }


    @Test
    void getProductListNoSuchProductListExceptionTestOK(){
        assertThrows(NoSuchProductListException.class, () -> productListService.getProductListById(999L));
    }

    @Test
    void addProductListTestOK(){
        this.productListService.addProductList(productListInt1);
        Optional<ProductList> optionalProductList = this.productListRepository.findByName(productListInt1.getName());
        if (optionalProductList.isPresent()) {
            ProductList added = optionalProductList.get();
            assertEquals("testInt_2", added.getName());
            assertEquals("testInt_2", added.getDescription());
        }
    }

    @Test
    void updateProductListTestOK(){
        this.productListService.updateProductList(productListInt.getName(), productListDtoInt);
        ProductList updatedProductList = this.productListService.getProductListByName(productListDtoInt.getName());
        assertNotNull(updatedProductList);
        assertEquals("testInt_3", updatedProductList.getName());
        assertEquals("testInt_3", updatedProductList.getDescription());
    }

    @Test
    void updateProductListNoSuchProductListExceptionTestOK(){
        assertThrows(NoSuchProductListException.class, () -> productListService.updateProductList(999L, productListDto));
    }
    @Test
    void deleteProductListTestOK() {
        Optional<ProductList> optionalProductList = Optional.ofNullable(this.productListRepository.findAll().get(0));
        if (optionalProductList.isPresent()) {
            ProductList productListDelete = optionalProductList.get();
            productListService.deleteProductList(productListDelete.getId());

            assertThrows(NoSuchProductListException.class, () -> productListService.getProductListById(productListDelete.getId()));
        }
    }

    @Test
    void deleteProductListNoSuchProductListExceptionTestOK(){
        assertThrows(NoSuchProductListException.class, () -> productListService.deleteProductList(999L));
    }

}
