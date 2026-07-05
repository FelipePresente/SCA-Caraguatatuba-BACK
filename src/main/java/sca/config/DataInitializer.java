package sca.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import sca.food.Food;
import sca.food.FoodRepository;
import sca.role.Role;
import sca.role.RoleRepository;
import sca.user.User;
import sca.user.UserRepository;

@Configuration
public class DataInitializer implements CommandLineRunner {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final FoodRepository foodRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(RoleRepository roleRepository, UserRepository userRepository,
            PasswordEncoder passwordEncoder, FoodRepository foodRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.foodRepository = foodRepository;
    }

    @Override
    public void run(String... args) {
        createRoles();
        createAdminUser();
        createDefaultFood();
    }

    public void createRoles() {
        String[] roleNames = { "school", "seduc_user", "admin" };
        for (String roleName : roleNames) {
            if (roleRepository.findByName(roleName).isEmpty()) {
                roleRepository.save(new Role(roleName));
                System.out.println("Cargo " + roleName + " criado");
            }
        }
    }

    public void createAdminUser() {
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

    // Essa função executa somente se nenhuma comida existir no banco
    public void createDefaultFood() {
        if (this.foodRepository.count() > 0) {
            return;
        }

        String[] foodNames = {
                "Feijão", "Arroz", "Macarrão", "Bife", "Frango", "Peixe", "Legumes", "Fruta",
                "Suco", "Pão", "Bolo", "Coxinha", "Pastel", "Salgado", "Refrigerante",
                "Água", "Café", "Chá", "Leite", "Iogurte"
        };
        Double[] foodPrices = {
                5.77, 6.32, 7.37, 15.43, 12.72, 13.37, 6.17, 10.22,
                8.47, 4.12, 11.07, 9.62, 10.22, 10.87, 13.37,
                2.17, 6.17, 5.12, 5.12, 8.47
        };
        for (int i = 0; i < foodNames.length; i++) {
            this.foodRepository.save(new Food(foodNames[i], foodPrices[i]));
            System.out.println("Comida " + foodNames[i] + " criada");
        }
    }
}