package ph.agh.tiwo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ph.agh.tiwo.entity.Product;
import ph.agh.tiwo.entity.Warehouse;
import ph.agh.tiwo.service.WarehouseService;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tiwo/warehouse")
@CrossOrigin(origins = "http://localhost:4200")
public class WarehouseController {

    private final WarehouseService warehouseService;


    public WarehouseController(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    @GetMapping("/getProducts")
    public ResponseEntity<List<Product>> getProducts(@RequestParam String name){
        Warehouse warehouse = warehouseService.getWarehouseByName(name);
        warehouse.getProducts().sort(Comparator.comparing(Product::getId));
        return new ResponseEntity<>(warehouse.getProducts().stream().sorted(Comparator.comparing(Product::getId))
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @PutMapping("/buyProduct")
    public ResponseEntity<Void> buyProduct(@RequestParam String productName, @RequestParam Double productQuantity){
        this.warehouseService.buyProduct(productName, productQuantity);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/addProduct")
    public ResponseEntity<Void> addProduct(@RequestParam String productName, @RequestParam Double productQuantity){
        this.warehouseService.addProduct(productName,productQuantity);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}