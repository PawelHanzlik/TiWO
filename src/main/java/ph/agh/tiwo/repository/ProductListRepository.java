package ph.agh.tiwo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ph.agh.tiwo.entity.ProductList;
import ph.agh.tiwo.entity.User;

import java.util.Optional;


@Repository
public interface ProductListRepository extends JpaRepository<ProductList, Long> {

    Optional<ProductList> findByName(String name);
    Optional<ProductList> findByNameAndUser(String name, User user);
}
