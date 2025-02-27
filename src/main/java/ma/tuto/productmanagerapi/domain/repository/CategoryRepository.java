package ma.tuto.productmanagerapi.domain.repository;

import ma.tuto.productmanagerapi.domain.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
