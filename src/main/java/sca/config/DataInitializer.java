package sca.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import sca.role.Role;
import sca.role.RoleRepository;
import sca.user.User;
import sca.user.UserRepository;

@Configuration
public class DataInitializer implements CommandLineRunner {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(RoleRepository roleRepository, UserRepository userRepository,
            PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (roleRepository.count() == 0) {
            roleRepository.save(new Role("school"));
            roleRepository.save(new Role("seduc_user"));
            roleRepository.save(new Role("admin"));
            System.out.println("Cargos 'school', 'seduc_user' e 'admin' criados");
        }

        if (roleRepository.findByName("admin").isPresent()) {
            Role adminRole = roleRepository.findByName("admin")
                    .orElseThrow(() -> new RuntimeException("Cargo 'admin' não encontrado"));

            if (userRepository.findByRoleName("admin").isEmpty()) {
                String encryptedPassword = passwordEncoder.encode("admin");
                User adminUser = new User("admin", encryptedPassword);
                adminUser.setRole(adminRole);
                userRepository.save(adminUser);
            }

            System.out.println("Usuário admin padrão criado");
        }
    }
}