package ph.agh.tiwo.UnitTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import ph.agh.tiwo.entity.ProductList;
import ph.agh.tiwo.exception.Classes.NoSuchProductListException;
import ph.agh.tiwo.exception.Classes.ProductListAlreadyExistsException;
import ph.agh.tiwo.repository.ProductListRepository;
import ph.agh.tiwo.service.ProductListService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static ph.agh.tiwo.DataProviders.ProductListDataProvider.*;

public class ProductListServiceUnitTests {

    @InjectMocks
    ProductListService productListService;

    @Mock
    ProductListRepository productListRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getProductListByIdTestOK(){
        when(productListRepository.findById(productListId)).thenReturn(Optional.ofNullable(productList));
        ProductList getProductList = this.productListService.getProductListById(productListId);
        assertNotNull(getProductList);
        productList = ProductList.builder().id(productListId).name("test_1")
                .products(products).description("test").build();
        assertEquals(1L, getProductList.getId());
        assertEquals("test_1", getProductList.getName());
        assertEquals(products, getProductList.getProducts());
        assertEquals("test", getProductList.getDescription());
    }

    @Test
    void getProductListByNameTestOK(){
        when(productListRepository.findByName(productList.getName())).thenReturn(Optional.ofNullable(productList));
        ProductList getProductList = this.productListService.getProductListByName(productList.getName());
        assertNotNull(getProductList);
        productList = ProductList.builder().id(productListId).name("test_1")
                .products(products).description("test").build();
        assertEquals(1L, getProductList.getId());
        assertEquals("test_1", getProductList.getName());
        assertEquals(products, getProductList.getProducts());
        assertEquals("test", getProductList.getDescription());
    }
    @Test
    void getProductListNoSuchProductListExceptionTestOK(){
        when(productListRepository.findById(productListId)).thenReturn(Optional.empty());
        assertThrows(NoSuchProductListException.class, () -> productListService.getProductListById(productListId));
    }

    @Test
    void getProductListByNameNoSuchProductListExceptionTestOK(){
        when(productListRepository.findByName(productList.getName())).thenReturn(Optional.empty());
        assertThrows(NoSuchProductListException.class, () -> productListService.getProductListByName(productList.getName()));
    }

    @Test
    void getAllProductsListOK(){
        when(productListRepository.findAll()).thenReturn(productLists);
        List<ProductList> productLists = this.productListService.getAllProductLists();
        assertEquals(productList,productLists.get(0));
        assertEquals(productList1,productLists.get(1));
    }
    @Test
    void addProductListTestOK(){
        when(productListRepository.save(any(ProductList.class))).thenReturn(productList1);
        ProductList newProductList = this.productListService.addProductList(productList1);
        assertNotNull(newProductList);
        assertEquals(2L, newProductList.getId());
        assertEquals("test_2", newProductList.getName());
        assertEquals(products, newProductList.getProducts());
        assertEquals("test_2", newProductList.getDescription());
    }

    @Test
    void addProductList_ProductAlreadyExistsException(){
        when(productListRepository.findByName(productList.getName())).thenReturn(Optional.ofNullable(productList));
        assertThrows(ProductListAlreadyExistsException.class, () -> productListService.addProductList(productList));
    }
    @Test
    void updateProductListByIdTestOK(){
        when(productListRepository.findById(productListId)).thenReturn(Optional.ofNullable(productList3));
        when(productListRepository.save(any(ProductList.class))).thenReturn(productList2);
        ProductList updatedProductList = this.productListService.updateProductList(productListId, productListDto);
        assertNotNull(updatedProductList);
        assertEquals(1L, updatedProductList.getId());
        assertEquals("test_3", updatedProductList.getName());
        assertEquals(products1, updatedProductList.getProducts());
        assertEquals("test_3", updatedProductList.getDescription());
    }

    @Test
    void updateProductListByNameTestOK(){
        when(productListRepository.findByName(productList3.getName())).thenReturn(Optional.ofNullable(productList3));
        when(productListRepository.save(any(ProductList.class))).thenReturn(productList2);
        ProductList updatedProductList = this.productListService.updateProductList(productList3.getName(), productListDto);
        assertNotNull(updatedProductList);
        assertEquals(1L, updatedProductList.getId());
        assertEquals("test_3", updatedProductList.getName());
        assertEquals(products1, updatedProductList.getProducts());
        assertEquals("test_3", updatedProductList.getDescription());
    }

    @Test
    void updateProductListNoSuchProductListExceptionTestOK(){
        when(productListRepository.findById(productListId)).thenReturn(Optional.empty());
        assertThrows(NoSuchProductListException.class, () -> productListService.updateProductList(productListId, productListDto));
    }

    @Test
    void updateProductByNameListNoSuchProductListExceptionTestOK(){
        when(productListRepository.findByName(productList.getName())).thenReturn(Optional.empty());
        assertThrows(NoSuchProductListException.class, () -> productListService.updateProductList(productList.getName(), productListDto));
    }

    @Test
    void deleteProductListByIdTestOK() {
        Optional<ProductList> optionalProductList = Optional.of(productList);

        when(productListRepository.findById(productListId)).thenReturn(optionalProductList);

        productListService.deleteProductList(productListId);

        Mockito.verify(productListRepository, times(1)).delete(optionalProductList.get());
    }

    @Test
    void deleteProductListByNameTestOK() {
        Optional<ProductList> optionalProductList = Optional.of(productList);

        when(productListRepository.findByName(productList.getName())).thenReturn(optionalProductList);

        productListService.deleteProductList(productList.getName());

        Mockito.verify(productListRepository, times(1)).delete(optionalProductList.get());
    }

    @Test
    void deleteProductListNoSuchProductListExceptionTestOK(){
        when(productListRepository.findById(productListId)).thenReturn(Optional.empty());
        assertThrows(NoSuchProductListException.class, () -> productListService.deleteProductList(productListId));
    }

    @Test
    void deleteProductByNameListNoSuchProductListExceptionTestOK(){
        when(productListRepository.findByName(productList.getName())).thenReturn(Optional.empty());
        assertThrows(NoSuchProductListException.class, () -> productListService.deleteProductList(productList.getName()));
    }

    @Test
    void buildProductList(){
        ProductList productList = this.productListService.buildProductList(productListDto,user);
        assertEquals(productList.getName(), productListDto.getName());
        assertEquals(productList.getDescription(), productListDto.getDescription());
    }

    @Test
    void updateProductListAddUser(){
        when(this.productListRepository.findByName("test")).thenReturn(Optional.empty());
        assertThrows(NoSuchProductListException.class,
                () -> productListService.updateProductListAddUser("test",user));
    }
}
