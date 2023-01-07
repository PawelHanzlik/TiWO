package ph.agh.tiwo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ph.agh.tiwo.entity.ProductList;


@Repository
public interface ProductListRepository extends JpaRepository<ProductList, Long> {
}
