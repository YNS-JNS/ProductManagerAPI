package ma.tuto.productmanagerapi.infrastructure.security;

import lombok.extern.slf4j.Slf4j;
import ma.tuto.productmanagerapi.domain.model.User;
import ma.tuto.productmanagerapi.domain.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userFounded = userRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("User not found!"));

        log.info("***** User founded *****: {}", userFounded);

        return org.springframework.security.core.userdetails.User
                .withUsername(userFounded.getUsername())
                .password(userFounded.getPassword())
                .roles(userFounded.getRole())
                .build();
    }
}
