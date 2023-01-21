package ph.agh.tiwo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ph.agh.tiwo.entity.Product;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ProductListDto {

    private String name;
    private List<Product> products;
    private String description;
    private LocalDate dueTo;
}
