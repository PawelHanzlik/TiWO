package ph.agh.tiwo.UnitTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import ph.agh.tiwo.dto.ProductDto;
import ph.agh.tiwo.entity.Product;
import ph.agh.tiwo.exception.Classes.NoSuchProductException;
import ph.agh.tiwo.repository.ProductRepository;
import ph.agh.tiwo.service.ProductService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static ph.agh.tiwo.DataProviders.ProductServiceDataProvider.*;
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
    void getProductByIdTestOK(){
        when(productRepository.findById(productId)).thenReturn(Optional.ofNullable(product));
        Product getProduct = this.productService.getProductById(productId);
        assertNotNull(getProduct);
        System.out.println(getProduct);
        assertEquals(1, getProduct.getId());
        assertEquals("test_1", getProduct.getName());
        assertEquals(5.0, getProduct.getCost());
        assertEquals(true, getProduct.getBought());
        assertEquals(2.0, getProduct.getQuantity());
        assertEquals("sztuk", getProduct.getType());
    }

    @Test
    void getProductByNameTestOK(){
        when(productRepository.findByName("test_1")).thenReturn(Optional.ofNullable(product));
        Product getProduct = this.productService.getProductByName("test_1");
        assertNotNull(getProduct);
        System.out.println(getProduct);
        assertEquals(1, getProduct.getId());
        assertEquals("test_1", getProduct.getName());
        assertEquals(5.0, getProduct.getCost());
    }

    @Test
    void getProductByIdNoSuchProductExceptionTestOK(){
        when(productRepository.findById(productId)).thenReturn(Optional.empty());
        assertThrows(NoSuchProductException.class, () -> productService.getProductById(productId));
    }

    @Test
    void getProductByNameNoSuchProductExceptionTestOK(){
        when(productRepository.findByName("test_1")).thenReturn(Optional.empty());
        assertThrows(NoSuchProductException.class, () -> productService.getProductByName("test_1"));
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
    void updateProductByIdTestOK(){
        when(productRepository.findById(productId)).thenReturn(Optional.ofNullable(product2));
        when(productRepository.save(any(Product.class))).thenReturn(product3);
        Product updatedProduct = this.productService.updateProduct(productId,product3);
        assertNotNull(updatedProduct);
        assertEquals(productId, updatedProduct.getId());
        assertEquals("test_4", updatedProduct.getName());
        assertEquals(20.0, updatedProduct.getCost());
    }

    @Test
    void updateProductByNameTestOK(){
        when(productRepository.findByName(product2.getName())).thenReturn(Optional.ofNullable(product2));
        when(productRepository.save(any(Product.class))).thenReturn(product3);
        Product updatedProduct = this.productService.updateProduct(product2.getName(),product3);
        assertNotNull(updatedProduct);
        assertEquals(productId, updatedProduct.getId());
        assertEquals("test_4", updatedProduct.getName());
        assertEquals(20.0, updatedProduct.getCost());
    }

    @Test
    void updateProductNoSuchProductExceptionTestOK(){
        when(productRepository.findById(productId)).thenReturn(Optional.empty());
        assertThrows(NoSuchProductException.class, () -> productService.updateProduct(productId, product3));
    }

    @Test
    void updateProductByNameNoSuchProductExceptionTestOK(){
        when(productRepository.findByName(product.getName())).thenReturn(Optional.empty());
        assertThrows(NoSuchProductException.class, () -> productService.updateProduct(product.getName(), product3));
    }

    @Test
    void deleteProductByIdTestOK() {
        Optional<Product> optionalProduct = Optional.of(product);

        when(productRepository.findById(productId)).thenReturn(optionalProduct);

        productService.deleteProduct(productId);

        Mockito.verify(productRepository, times(1)).delete(optionalProduct.get());
    }

    @Test
    void deleteProductByNameTestOK() {
        Optional<Product> optionalProduct = Optional.of(product);

        when(productRepository.findByName(product.getName())).thenReturn(optionalProduct);

        productService.deleteProduct(product.getName());

        Mockito.verify(productRepository, times(1)).delete(optionalProduct.get());
    }

    @Test
    void deleteProductNoSuchProductExceptionTestOK(){
        when(productRepository.findById(productId)).thenReturn(Optional.empty());
        assertThrows(NoSuchProductException.class, () -> productService.deleteProduct(productId));
    }

    @Test
    void deleteProductByNameNoSuchProductExceptionTestOK(){
        when(productRepository.findByName(product.getName())).thenReturn(Optional.empty());
        assertThrows(NoSuchProductException.class, () -> productService.deleteProduct(product.getName()));
    }

    @Test
    void buildProductOk(){
        Product product = this.productService.buildProduct(
                new ProductDto("test",1.0,"test", "test"),productList);
        assertEquals(product.getName(), "test");
        assertEquals(product.getQuantity(),1.0);
        assertEquals(product.getType(), "test");
    }

    @Test
    void updateProductAddProductNameNoSuchProductExceptionTestOK(){
        when(productRepository.findByName("test")).thenReturn(Optional.empty());
        assertThrows(NoSuchProductException.class,
                () -> productService.updateProductAddProductList("test",productList));
    }
}
