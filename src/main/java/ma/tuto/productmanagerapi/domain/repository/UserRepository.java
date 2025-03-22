package ma.tuto.productmanagerapi.domain.repository;

import ma.tuto.productmanagerapi.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository pour l'entité User.
 * Permet d'accéder aux données des utilisateurs en base.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Méthode pour trouver un utilisateur par son nom d'utilisateur
    Optional<User> findByUsername(String username);
}
