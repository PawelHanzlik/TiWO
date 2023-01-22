package ph.agh.tiwo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ph.agh.tiwo.dto.ProductListDto;
import ph.agh.tiwo.entity.ProductList;
import ph.agh.tiwo.entity.User;
import ph.agh.tiwo.exception.Classes.NoSuchUserException;
import ph.agh.tiwo.repository.UserRepository;
import ph.agh.tiwo.service.ProductListService;

import java.util.Optional;

@RestController
@RequestMapping("/tiwo/productList")
@CrossOrigin(origins = "http://localhost:4200")
public class ProductListController {

    // update listy

    private final ProductListService productListService;
    private final UserRepository userRepository;

    public ProductListController(ProductListService productListService, UserRepository userRepository) {
        this.productListService = productListService;
        this.userRepository = userRepository;
    }

    @PostMapping("/addProductList")
    public ResponseEntity<ProductList> addProductList(@RequestBody ProductListDto productListDto,
                                                  @RequestParam String userEmail){
        Optional<User> optionalUser = this.userRepository.findByEmail(userEmail);
        if (optionalUser.isPresent()){
            User user = optionalUser.get();
            ProductList productList = this.productListService.buildProductList(productListDto, user);
            this.productListService.addProductList(productList);
            return new ResponseEntity<>(productList, HttpStatus.CREATED);
        }
        else
            throw new NoSuchUserException();
    }

    @PutMapping("/updateProductList")
    public ResponseEntity<ProductList> updateProductList(@RequestParam String listId, @RequestBody ProductListDto productListDto) {
        ProductList productList = this.productListService.updateProductList(Long.parseLong(listId),productListDto);
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    @DeleteMapping("/deleteProductList")
    public ResponseEntity<Void> deleteProductList(@RequestParam Long listId) {
        this.productListService.deleteProductList(listId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
