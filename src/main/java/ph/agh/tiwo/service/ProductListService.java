package ph.agh.tiwo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ph.agh.tiwo.dto.ProductListDto;
import ph.agh.tiwo.entity.ProductList;
import ph.agh.tiwo.exception.Classes.NoSuchProductListException;
import ph.agh.tiwo.repository.ProductListRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProductListService {

    private final ProductListRepository productListRepository;
    @Autowired
    public ProductListService(ProductListRepository productListRepository) {
        this.productListRepository = productListRepository;
    }
    
    public ProductList getProductListById(Long productListId) throws NoSuchProductListException {
        Optional<ProductList> productListOptional = this.productListRepository.findById(productListId);
        if (productListOptional.isEmpty()){
            throw new NoSuchProductListException();
        }
        return productListOptional.get();
    }

    public List<ProductList> getAllProductLists() {
        return this.productListRepository.findAll();
    }
    
    public ProductList addProductList(ProductList productList) {
        return this.productListRepository.save(productList);
    }

    public ProductList updateProductList(Long productListId, ProductListDto productListDto) throws NoSuchProductListException {
        Optional<ProductList> productListOptional = this.productListRepository.findById(productListId);
        if (productListOptional.isEmpty()){
            throw new NoSuchProductListException();
        }
        ProductList productList = productListOptional.get();
        productList.setName(productListDto.getName());
        productList.setProducts(productListDto.getProducts());
        productList.setDescription(productListDto.getDescription());
        return this.productListRepository.save(productList);
    }

    public void deleteProductList(Long productListId) throws NoSuchProductListException {
        Optional<ProductList> productListOptional = this.productListRepository.findById(productListId);
        if (productListOptional.isEmpty()){
            throw new NoSuchProductListException();
        }
        else{
            ProductList productList = productListOptional.get();
            this.productListRepository.delete(productList);
        }
    }

}
