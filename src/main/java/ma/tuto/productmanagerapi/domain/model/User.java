package ma.tuto.productmanagerapi.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entité représentant un utilisateur.
 * Les attributs username, email, password et role sont stockés en base.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String email;
    private String password;
    // Rôle de l'utilisateur ("USER" ou "ADMIN") sans préfixe "ROLE_"
    private String role;
}
