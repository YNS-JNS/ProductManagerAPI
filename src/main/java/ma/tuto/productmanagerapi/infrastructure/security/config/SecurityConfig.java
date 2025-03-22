package ma.tuto.productmanagerapi.infrastructure.security.config;

import ma.tuto.productmanagerapi.infrastructure.security.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Classe de configuration de la sécurité Spring.
 * Configure notamment le SecurityFilterChain, le DaoAuthenticationProvider et l'AuthenticationManager.
 */
@Configuration
@EnableWebSecurity // Active la configuration de sécurité web dans Spring
public class SecurityConfig {

    // Liste des endpoints publics qui ne nécessitent pas d'authentification
    private static final String[] AUTH_WHITELIST = {
            // -- Swagger UI v2
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            // -- Swagger UI v3 (OpenAPI)
            "/v3/api-docs/**",
            "/swagger-ui/**",
            // other public endpoints of your API may be appended to this array
            "/api/v1/public/**",
            "/h2-console/**", // DB H2
    };

    // ***********************************************************************
    private final CustomUserDetailsService customUserDetailsService;
    private final PasswordEncoder passwordEncoder;

    // Injection des dépendances via le constructeur
    public SecurityConfig(CustomUserDetailsService customUserDetailsService, PasswordEncoder passwordEncoder) {
        this.customUserDetailsService = customUserDetailsService;
        this.passwordEncoder = passwordEncoder;
    }
    // ***********************************************************************

    /**
     * Déclare un DaoAuthenticationProvider qui utilise CustomUserDetailsService pour charger l'utilisateur
     * et PasswordEncoder pour vérifier le mot de passe.
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();

        // Spécifie le service pour récupérer les informations de l'utilisateur
        authenticationProvider.setUserDetailsService(customUserDetailsService);

        // Définit l'encodeur utilisé pour comparer les mots de passe
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return authenticationProvider;
    }

    ;

    /**
     * Expose l'AuthenticationManager, utilisé par Spring Security pour gérer l'authentification.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    /**
     * Configure le SecurityFilterChain pour définir les règles de sécurité HTTP.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Désactive la protection CSRF, utile pour une API REST stateless
                .csrf(AbstractHttpConfigurer::disable)
                // Configure la gestion de session pour qu'elle soit sans état (stateless)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Pas de sessions
                // Définit les règles d'autorisation sur les endpoints
                .authorizeHttpRequests(
                        auth -> auth
                                .requestMatchers(AUTH_WHITELIST).permitAll() // Endpoints publics (sans authentification)
                                .requestMatchers("/api/v1/private-user/**").hasAnyRole("USER", "ADMIN") // (avec authentification et role USER ou ADMIN)
                                .requestMatchers("/api/v1/private-admin/**").hasRole("ADMIN") // (avec authentification et role ADMIN )
                                // Toute autre requête nécessite une authentification
                                .anyRequest().authenticated()
                )
                .httpBasic(httpBasic -> {
                })
                //.formLogin(AbstractHttpConfigurer::disable) // Désactivation de l'authentification par formulaire
                .formLogin(formLogin -> {
                }) // Désactivation de l'authentification par formulaire
                // Ajout de la configuration pour autoriser les frames
                .headers(headers -> {
                    headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable);
                    // headers.frameOptions(f-> f.disable());
                });
        return http.build();

        /**
         * Nb:
         * H2 Console utilise des frames pour afficher son interface.
         * Par défaut, Spring Security bloque l'affichage en mode frame pour des raisons de sécurité (via la configuration des headers HTTP)
         */
    }
}
