package ph.agh.tiwo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ph.agh.tiwo.entity.User;
import ph.agh.tiwo.entity.Warehouse;
import ph.agh.tiwo.exception.Classes.NoSuchWarehouseException;
import ph.agh.tiwo.exception.Classes.UserAlreadyExistsException;
import ph.agh.tiwo.exception.Classes.WarehouseAlreadyExistsException;
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
}
