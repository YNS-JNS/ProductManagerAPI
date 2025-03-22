package ma.tuto.productmanagerapi.infrastructure.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configuration globale pour le PasswordEncoder.
 * Ce bean sera utilisé pour encoder et comparer les mots de passe.
 */
@Configuration
public class PasswordEncoderConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        // BCrypt est un algorithme robuste de hachage
        return new BCryptPasswordEncoder();
    }

}
