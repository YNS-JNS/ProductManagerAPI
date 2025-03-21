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

@Configuration
@EnableWebSecurity // Active la configuration de sécurité web dans Spring
public class SecurityConfig {

    // Declare all public endpoints
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

    private final CustomUserDetailsService customUserDetailsService;
    private final PasswordEncoder passwordEncoder;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService, PasswordEncoder passwordEncoder) {
        this.customUserDetailsService = customUserDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    // Définition du DaoAuthenticationProvider pour expliciter la source d'authentification
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(customUserDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return authenticationProvider;
    };

    // Définition de l'AuthenticationManager
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    // Configuration de la SecurityFilterChain
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // Désactiver CSRF pour API REST
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Pas de sessions
                .authorizeHttpRequests(
                        auth -> auth
                                .requestMatchers(AUTH_WHITELIST).permitAll() // (sans authentification)
                                .requestMatchers("/api/v1/private-user/**").hasAnyRole("USER", "ADMIN") // (avec authentification et role USER ou ADMIN)
                                .requestMatchers("/api/v1/private-admin/**").hasRole("ADMIN") // (avec authentification et role ADMIN )
                                .anyRequest().authenticated()
                )
                .httpBasic(httpBasic -> {})
                //.formLogin(AbstractHttpConfigurer::disable) // Désactivation de l'authentification par formulaire
                .formLogin(formLogin -> {}) // Désactivation de l'authentification par formulaire
                // Ajout de la configuration pour autoriser les frames
                .headers(headers -> {
                    headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable);
                    // headers.frameOptions(f-> f.disable());
                });
        return http.build();

        /*
        * Nb:
        * *********************************************************************************************************************************
        * H2 Console utilise des frames pour afficher son interface.
        * Par défaut, Spring Security bloque l'affichage en mode frame pour des raisons de sécurité (via la configuration des headers HTTP)
        * *********************************************************************************************************************************
        *
        * */
    }
}
