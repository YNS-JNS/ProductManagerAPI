package ma.tuto.productmanagerapi.infrastructure.security.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfig {

    // Injecte Password encoder
    private final PasswordEncoder passwordEncoder;

    public SecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

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
            "/swagger-ui/**"
            // other public endpoints of your API may be appended to this array
    };

    // Create users in memory
    @Bean
    UserDetailsService userDetailsService() {

        // On a créé deux utilisateurs
        UserDetails adminUser = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin123"))
                .authorities("ROLE_ADMIN")
                .build();

        UserDetails simpleUser = User.builder()
                .username("user")
                .password(passwordEncoder.encode("user123"))
                .authorities("ROLE_USER")
                .build();

        // log :
        log.info("admin user: {}", adminUser);
        log.info("simple user: {}", simpleUser);

        // On a persisté les deux utilisateurs en mémoire
        return new InMemoryUserDetailsManager(adminUser, simpleUser);
    }

    // FilterChain
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // Désactiver CSRF pour API REST
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Pas de sessions
                .authorizeHttpRequests(
                        auth -> auth
                                //.requestMatchers(AUTH_WHITELIST).permitAll()
                                .requestMatchers("/api/v1/public/**").permitAll() // (sans authentification)
                                .requestMatchers("/api/v1/private-user/**").hasAuthority("ROLE_USER") // (avec authentification et autorité USER)
                                .requestMatchers("/api/v1/private-admin/**").hasAuthority("ROLE_ADMIN") // (avec authentification et autorité ADMIN )
                                .requestMatchers("/api/v1/ressources/**").authenticated() // (avec authentification)
                        //.anyRequest().authenticated()
                )
                .httpBasic(httpBasic -> {
                })
                .formLogin(AbstractHttpConfigurer::disable); // Désactivation de l'authentification par formulaire
        return http.build();
    }
}
