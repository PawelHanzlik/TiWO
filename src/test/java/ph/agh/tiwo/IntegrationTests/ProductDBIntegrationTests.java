package ph.agh.tiwo.IntegrationTests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ph.agh.tiwo.entity.Product;
import ph.agh.tiwo.exception.Classes.NoSuchProductException;
import ph.agh.tiwo.repository.ProductRepository;
import ph.agh.tiwo.service.ProductService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static ph.agh.tiwo.DataProviders.ProductServiceDataProvider.*;

@SpringBootTest
public class ProductDBIntegrationTests {

    @Autowired
    ProductService productService;

    @Autowired
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        this.productRepository.save(productInt);
    }

    @AfterEach
    void cleanUp() {
        this.productRepository.deleteAll();
    }

    @Test
    void getProductByNameTestOK(){
        Product getProduct = this.productService.getProductByName("testInt_1");
        assertNotNull(getProduct);
        System.out.println(getProduct);
        assertEquals("testInt_1", getProduct.getName());
        assertEquals(5.0, getProduct.getCost());
    }

    @Test
    void getProductByIdNoSuchProductExceptionTestOK(){
        assertThrows(NoSuchProductException.class, () -> productService.getProductById(999L));
    }

    @Test
    void getProductByNameNoSuchProductExceptionTestOK(){
        assertThrows(NoSuchProductException.class, () -> productService.getProductByName("qqqqqqqqq"));
    }
    @Test
    void addProductTestOK(){
        this.productService.addProduct(productInt1);
        Optional<Product> optionalProduct = this.productRepository.findByName("test_2");
        if (optionalProduct.isPresent()) {
            Product newProduct = optionalProduct.get();
            assertNotNull(newProduct);
            assertEquals("test_2", newProduct.getName());
            assertEquals(10.0, newProduct.getCost());
        }
    }

    @Test
    void updateProductTestOK(){
        this.productService.updateProduct(productInt.getName(),productInt1);
        Optional<Product> optionalProduct = this.productRepository.findByName("test_4");
        if (optionalProduct.isPresent()) {
            Product updatedProduct = optionalProduct.get();
            assertNotNull(updatedProduct);
            assertEquals("testInt_2", updatedProduct.getName());
            assertEquals(10.0, updatedProduct.getCost());
        }
    }

    @Test
    void updateProductNoSuchProductExceptionTestOK(){
        assertThrows(NoSuchProductException.class, () -> productService.updateProduct(999L, product3));
    }

    @Test
    void deleteProductTestOK() {
        Optional<Product> optionalProduct = Optional.ofNullable(this.productRepository.findAll().get(0));
        if (optionalProduct.isPresent()) {
            Product productDelete = optionalProduct.get();
            productService.deleteProduct(productDelete.getId());

            assertThrows(NoSuchProductException.class, () -> productService.getProductById(productDelete.getId()));
        }
    }

    @Test
    void deleteProductNoSuchProductExceptionTestOK(){
        assertThrows(NoSuchProductException.class, () -> productService.deleteProduct(999L));
    }

}
