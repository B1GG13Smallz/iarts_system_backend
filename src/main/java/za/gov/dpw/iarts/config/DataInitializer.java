package za.gov.dpw.iarts.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import za.gov.dpw.iarts.entity.Role;
import za.gov.dpw.iarts.entity.User;
import za.gov.dpw.iarts.constants.RoleNames;
import za.gov.dpw.iarts.repository.RoleRepository;
import za.gov.dpw.iarts.repository.UserRepository;
import java.util.HashSet;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {
    private final PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner seedRoles(RoleRepository roleRepository, UserRepository userRepository) {
        return args -> {
            RoleNames.ALL.forEach(roleName -> roleRepository.findByName(roleName).orElseGet(() -> {
                Role role = new Role();
                role.setName(roleName);
                role.setDescription(roleName.replace('_', ' '));
                return roleRepository.save(role);
            }));

            if (userRepository.findByUsername("admin").isEmpty()) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setEmail("admin@iarts.local");
                admin.setFullName("IARTS Administrator");
                admin.setPassword(passwordEncoder.encode("Admin@123"));
                admin.setRoles(new HashSet<>(roleRepository.findAll()));
                userRepository.save(admin);
            }
        };
    }
}
