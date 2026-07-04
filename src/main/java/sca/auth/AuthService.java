package sca.auth;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import sca.auth.dto.AuthLoginDTO;
import sca.exception.InvalidCredentialsException;
import sca.user.User;
import sca.user.UserRepository;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, TokenService tokenService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }

    public String login(AuthLoginDTO data) {
        User user = userRepository.findByUsername(data.username());

        if (user == null || !this.passwordEncoder.matches(data.password(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid credentials");
        }

        return this.tokenService.generateToken(user);
    }
}
