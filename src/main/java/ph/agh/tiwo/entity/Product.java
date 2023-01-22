package ph.agh.tiwo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "Products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "cost")
    private Double cost;

    @Column(name = "bought")
    private Boolean bought;

    @Column(name = "quantity")
    private Double quantity;

    @Column(name = "type")
    private String type;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.MERGE)
    @JsonIgnore
    private ProductList productList;
}
