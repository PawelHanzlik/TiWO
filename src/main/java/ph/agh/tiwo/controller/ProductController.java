package ph.agh.tiwo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ph.agh.tiwo.dto.ProductDto;
import ph.agh.tiwo.entity.Product;
import ph.agh.tiwo.entity.ProductList;
import ph.agh.tiwo.exception.Classes.NoSuchProductListException;
import ph.agh.tiwo.repository.ProductListRepository;
import ph.agh.tiwo.repository.ProductRepository;
import ph.agh.tiwo.service.ProductService;

import java.util.Optional;

@RestController
@RequestMapping("/tiwo/product")
@CrossOrigin(origins = "http://localhost:4200")
public class ProductController {

    private final ProductService productService;

    private final ProductListRepository productListRepository;
    private final ProductRepository productRepository;

    public ProductController(ProductService productService, ProductListRepository productListRepository,
                             ProductRepository productRepository) {
        this.productService = productService;
        this.productListRepository = productListRepository;
        this.productRepository = productRepository;
    }

    @PostMapping("/addProduct")
    public ResponseEntity<Product> addProduct(@RequestBody ProductDto productDto, @RequestParam String listName){
        Optional<ProductList> optionalProductList = this.productListRepository.findByName(listName);
        if (optionalProductList.isPresent()){
            ProductList productList = optionalProductList.get();
            Product product = this.productService.buildProduct(productDto, productList);
            Product addedProduct = this.productService.addProduct(product);
            return new ResponseEntity<>(addedProduct, HttpStatus.CREATED);
        }
        else
            throw new NoSuchProductListException();
    }

    @PutMapping("/updateProduct")
    public ResponseEntity<Product> updateProduct(@RequestParam String productId, @RequestBody ProductDto productDto){
        Product product = this.productService.updateProduct(Long.parseLong(productId), Product.builder()
                .name(productDto.getName()).type(productDto.getType()).quantity(productDto.getQuantity()).build());
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PutMapping("/updateProductBought")
    public ResponseEntity<Product> updateProduct(@RequestParam String productId){
        Product product = this.productService.getProductById(Long.valueOf(productId));
        product.setBought(!product.getBought());
        this.productRepository.save(product);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @DeleteMapping("/deleteProduct")
    public ResponseEntity<Void> deleteProduct(@RequestParam Long productId){
        this.productService.deleteProduct(productId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
