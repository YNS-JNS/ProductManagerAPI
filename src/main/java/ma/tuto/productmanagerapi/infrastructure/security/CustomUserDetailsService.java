package ma.tuto.productmanagerapi.infrastructure.security;

import lombok.extern.slf4j.Slf4j;
import ma.tuto.productmanagerapi.domain.model.User;
import ma.tuto.productmanagerapi.domain.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Implémentation de UserDetailsService pour charger les informations d'un utilisateur.
 * Cette classe est utilisée par le DaoAuthenticationProvider lors de l'authentification.
 */
@Slf4j
@Service
public class CustomUserDetailsService implements UserDetailsService {

    // ***********************************************************************
    private final UserRepository userRepository;
    // Injection du repository par constructeur
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    // ***********************************************************************

    /**
     * Charge l'utilisateur en fonction du nom d'utilisateur fourni.
     * Si l'utilisateur n'est pas trouvé, une exception est levée.
     *
     * @param username Le nom d'utilisateur fourni dans la requête d'authentification.
     * @return Un objet UserDetails contenant les informations de l'utilisateur.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Recherche de l'utilisateur dans la base de données
        User userFounded = userRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("User not found!"));

        log.info("***** User founded *****: {}", userFounded);

        // Construction de l'objet UserDetails en spécifiant le nom d'utilisateur, le mot de passe encodé
        // et les rôles. Ici, on suppose que userFounded.getRole() renvoie "USER" ou "ADMIN"
        // La méthode .roles() ajoute automatiquement le préfixe "ROLE_".
        return org.springframework.security.core.userdetails.User
                .withUsername(userFounded.getUsername())
                .password(userFounded.getPassword())
                .roles(userFounded.getRole())
                .build();
    }
}
