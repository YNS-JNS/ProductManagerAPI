package ma.tuto.productmanagerapi.domain.repository;

import ma.tuto.productmanagerapi.domain.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
