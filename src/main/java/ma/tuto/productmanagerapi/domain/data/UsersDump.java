package ma.tuto.productmanagerapi.domain.data;

import ma.tuto.productmanagerapi.domain.model.User;
import ma.tuto.productmanagerapi.domain.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UsersDump implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UsersDump(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {

        User user = User.builder()
                .username("admin")
                .email("admin@admin.com")
                .password(passwordEncoder.encode("admin123"))
                .role("ADMIN")
                .build();
        userRepository.save(user);
    }
}
