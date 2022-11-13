package ph.agh.tiwo.UnitTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import ph.agh.tiwo.entity.Product;
import ph.agh.tiwo.exception.Classes.NoSuchProductException;
import ph.agh.tiwo.repository.ProductRepository;
import ph.agh.tiwo.service.ProductService;

import java.util.List;
import java.util.Optional;

import static ph.agh.tiwo.DataProviders.ProductServiceDataProvider.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
public class ProductServiceUnitTests {

    @InjectMocks
    ProductService productService;

    @Mock
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getProductTestOK(){
        when(productRepository.findById(productId)).thenReturn(Optional.ofNullable(product));
        Product getProduct = this.productService.getProductById(productId);
        assertNotNull(getProduct);
        assertEquals(1, getProduct.getId());
        assertEquals("test_1", getProduct.getName());
        assertEquals(5.0, getProduct.getCost());
    }


    @Test
    void getProductNoSuchProductExceptionTestOK(){
        when(productRepository.findById(productId)).thenReturn(Optional.empty());
        assertThrows(NoSuchProductException.class, () -> productService.getProductById(productId));
    }

    @Test
    void getAllProductsOK(){
        when(productRepository.findAll()).thenReturn(products);
        List<Product> productList = this.productService.getAllProducts();
        assertEquals(product2,productList.get(0));
        assertEquals(product3,productList.get(1));
    }
    @Test
    void addProductTestOK(){
        when(productRepository.save(any(Product.class))).thenReturn(product1);
        Product newProduct = this.productService.addProduct(product1);
        assertNotNull(newProduct);
        assertEquals(1, newProduct.getId());
        assertEquals("test_2", newProduct.getName());
        assertEquals(10.0, newProduct.getCost());
    }

    @Test
    void deleteProductTestOK() {
        Optional<Product> optionalProduct = Optional.of(product);

        when(productRepository.findById(productId)).thenReturn(optionalProduct);

        productService.deleteProduct(productId);

        Mockito.verify(productRepository, times(1)).delete(optionalProduct.get());
    }

    @Test
    void deleteProductNoSuchProductExceptionTestOK(){
        when(productRepository.findById(productId)).thenReturn(Optional.empty());
        assertThrows(NoSuchProductException.class, () -> productService.deleteProduct(productId));
    }

}
