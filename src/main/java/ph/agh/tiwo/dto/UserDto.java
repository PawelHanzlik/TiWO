package ph.agh.tiwo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ph.agh.tiwo.entity.ProductList;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class UserDto {

    private String name;
    private String surname;
    private String email;
    private Set<ProductList> productLists;
}
