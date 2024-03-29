package ph.agh.tiwo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ph.agh.tiwo.dto.ProductDto;
import ph.agh.tiwo.entity.Product;
import ph.agh.tiwo.entity.ProductList;
import ph.agh.tiwo.entity.Warehouse;
import ph.agh.tiwo.exception.Classes.NoSuchProductException;
import ph.agh.tiwo.repository.ProductRepository;
import ph.agh.tiwo.util.ProductMap;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product getProductById(Long productId) throws NoSuchProductException {
        Optional<Product> productOptional = this.productRepository.findById(productId);
        if (productOptional.isEmpty()) {
            throw new NoSuchProductException();
        }
        return productOptional.get();
    }

    public Product getProductByName(String productName) throws NoSuchProductException {
        Optional<Product> productOptional = this.productRepository.findByName(productName);
        if (productOptional.isEmpty()) {
            throw new NoSuchProductException();
        }
        return productOptional.get();
    }

    public List<Product> getAllProducts() {
        return this.productRepository.findAll();
    }

    public Product addProduct(Product product){
        return this.productRepository.save(product);
    }

    public Product updateProduct(Long productId, Product productToUpdate) throws NoSuchProductException {
        Optional<Product> productOptional = this.productRepository.findById(productId);
        if (productOptional.isEmpty()) {
            throw new NoSuchProductException();
        }
        Product product = productOptional.get();
        product.setName(productToUpdate.getName());
        product.setCost(ProductMap.getCost(productToUpdate.getName()));
        product.setBought(false);
        product.setQuantity(productToUpdate.getQuantity());
        product.setType(productToUpdate.getType());
        product.setUrl(ProductMap.getUrl(productToUpdate.getName()));
        return this.productRepository.save(product);
    }

    public Product updateProduct(String productName, Product productToUpdate) throws NoSuchProductException {
        Optional<Product> productOptional = this.productRepository.findByName(productName);
        if (productOptional.isEmpty()) {
            throw new NoSuchProductException();
        }
        Product product = productOptional.get();
        product.setName(productToUpdate.getName());
        product.setCost(ProductMap.getCost(productToUpdate.getName()));
        product.setBought(false);
        product.setQuantity(productToUpdate.getQuantity());
        product.setType(productToUpdate.getType());
        product.setUrl(ProductMap.getUrl(productToUpdate.getName()));
        return this.productRepository.save(product);
    }

    public void deleteProduct(Long productId) throws NoSuchProductException {
        Optional<Product> productOptional = this.productRepository.findById(productId);
        if (productOptional.isEmpty()) {
            throw new NoSuchProductException();
        } else {
            Product product = productOptional.get();
            this.productRepository.delete(product);
        }
    }

    public void deleteProduct(String productName) throws NoSuchProductException {
        Optional<Product> productOptional = this.productRepository.findByName(productName);
        if (productOptional.isEmpty()) {
            throw new NoSuchProductException();
        } else {
            Product product = productOptional.get();
            this.productRepository.delete(product);
        }

    }

    public void  updateProductAddProductList(String productName, ProductList productList) throws NoSuchProductException {
        Optional<Product> productOptional = this.productRepository.findByName(productName);
        if (productOptional.isEmpty()) {
            throw new NoSuchProductException();
        }
        Product product = productOptional.get();
        product.setProductList(productList);
        this.productRepository.save(product);
    }

    public Product buildProduct(ProductDto productDto, ProductList productList){
        return Product.builder().name(productDto.getName()).quantity(productDto.getQuantity()).cost(ProductMap.getCost(productDto.getName()))
                .type(productDto.getType()).bought(false).url(ProductMap.getUrl(productDto.getName())).productList(productList).build();
    }

    public void  updateProductAddWarehouse(String productName, Warehouse warehouse) throws NoSuchProductException {
        Optional<Product> productOptional = this.productRepository.findByName(productName);
        if (productOptional.isEmpty()) {
            throw new NoSuchProductException();
        }
        Product product = productOptional.get();
        product.setWarehouse(warehouse);
        this.productRepository.save(product);
    }

    public Product buildProductToWarehouse(ProductDto productDto, Warehouse warehouse){
        return Product.builder().name(productDto.getName()).quantity(productDto.getQuantity()).cost(ProductMap.getCost(productDto.getName()))
                .type(productDto.getType()).bought(false).url(ProductMap.getUrl(productDto.getName())).warehouse(warehouse).build();
    }

}
