package sca.user;

import java.util.List;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import sca.auth.TokenService;
import sca.exception.ConflictException;
import sca.exception.InvalidCredentialsException;
import sca.exception.ResourceNotFoundException;
import sca.role.Role;
import sca.role.RoleRepository;
import sca.user.dto.UserResponseDTO;
import sca.user.dto.UserSignUpDTO;
import sca.user.dto.UserUpdateDTO;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, TokenService tokenService,
            RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    public List<UserResponseDTO> findAll() {
        List<User> users = this.userRepository.findAll();

        return users.stream()
                .map(UserResponseDTO::fromEntity)
                .toList();
    }

    public Void create(UserSignUpDTO data) {
        if (this.userRepository.existsByUsername(data.username())) {
            throw new ConflictException("Nome de usuário já existe");
        }

        String encryptedPassword = this.passwordEncoder.encode(data.password());

        User newUser = new User(data.username(), encryptedPassword);

        Role role = this.roleRepository.findByName(data.role())
                .orElseThrow(() -> new ResourceNotFoundException("Cargo não encontrado"));

        newUser.setRole(role);

        return this.userRepository.save(newUser) != null ? null : null;
    }

    public UserResponseDTO update(UserUpdateDTO data) {
        User user = this.userRepository.findById(data.id())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        if (data.newUsername() != null && !data.newUsername().isBlank()) {
            if (data.newUsername().equals(user.getUsername())) {
                throw new InvalidCredentialsException("O novo nome de usuário deve ser diferente");
            }

            if (this.userRepository.existsByUsername(data.newUsername())) {
                throw new ConflictException("Nome de usuário já existe");
            }

            user.setUsername(data.newUsername());
        }

        if (data.newPassword() != null && !data.newPassword().isBlank()) {
            if (this.passwordEncoder.matches(data.newPassword(), user.getPassword())) {
                throw new InvalidCredentialsException("A nova senha deve ser diferente");
            }

            if (data.newUsername() != null && !data.newUsername().isBlank()) {
                if (this.userRepository.existsByUsername(data.newUsername())) {
                    throw new ConflictException("Nome de usuário já existe");
                }

                user.setUsername(data.newUsername());
            }
        }

        if (data.newPassword() != null && !data.newPassword().isBlank()) {
            String encryptedNewPassword = this.passwordEncoder.encode(data.newPassword());

            user.setPassword(encryptedNewPassword);
        }

        this.userRepository.save(user);

        return UserResponseDTO.fromEntity(user);
    }

    public void delete(UUID id) {
        User user = this.userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        this.userRepository.delete(user);
    }
}
