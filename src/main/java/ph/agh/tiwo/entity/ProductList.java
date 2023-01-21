package ph.agh.tiwo.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "Product_List")
public class ProductList {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "productList" , fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Product> products;

    @Column(name = "description")
    private String description;

    @Column(name = "dueTo")
    private LocalDate dueTo;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.MERGE)
    @JsonIgnore
    private User user;

}
