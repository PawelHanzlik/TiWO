package ph.agh.tiwo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ph.agh.tiwo.entity.Warehouse;
import ph.agh.tiwo.exception.Classes.*;
import ph.agh.tiwo.repository.WarehouseRepository;

import java.util.Optional;

@Service
public class WarehouseService {

    private final WarehouseRepository warehouseRepository;
    @Autowired
    public WarehouseService(WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }

    public Warehouse getWarehouseByName(String warehouseName) throws NoSuchWarehouseException {
        Optional<Warehouse> warehouseOptional = this.warehouseRepository.findByName(warehouseName);
        if (warehouseOptional.isEmpty()){
            throw new NoSuchWarehouseException();
        }
        return warehouseOptional.get();
    }

    public Warehouse addWarehouse(Warehouse warehouse) throws WarehouseAlreadyExistsException {
        Optional<Warehouse> warehouseOptional = this.warehouseRepository.findByName(warehouse.getName());
        if (warehouseOptional.isPresent()){
            throw new WarehouseAlreadyExistsException();
        }
        return this.warehouseRepository.save(warehouse);
    }

    public void buyProduct(String productName, Double productQuantity){
        Optional<Warehouse> warehouseOptional = this.warehouseRepository.findByName("testWarehouse");
        if (warehouseOptional.isEmpty()){
            throw new NoSuchWarehouseException();
        }else {
            Warehouse warehouse = warehouseOptional.get();
            for (int i = 0; i < warehouse.getProducts().size(); i++){
                if (warehouse.getProducts().get(i).getName().equals(productName)){
                    if (warehouse.getProducts().get(i).getQuantity() - productQuantity > 0){
                        warehouse.getProducts().get(i).setQuantity(warehouse.getProducts().get(i).getQuantity() - productQuantity);
                    }
                    else {
                        throw new NegativeQuantityException();
                    }
                }
                this.warehouseRepository.save(warehouse);
            }
        }
    }
}
