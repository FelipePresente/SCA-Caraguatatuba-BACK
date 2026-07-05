package sca.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserSignUpDTO(
    @NotBlank(message = "O cargo não deve estar em branco")
    @Size(min = 4, max = 16, message = "O cargo deve ter entre 4 e 16 caracteres")
    String role,

    @NotBlank(message = "O nome de usuário não deve estar em branco")
    @Size(min = 4, max = 16, message = "O nome de usuário deve ter entre 4 e 16 caracteres")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "O nome de usuário deve conter apenas letras e números, sem espaços")
    String username,

    @NotBlank(message = "A senha não deve estar em branco")
    @Size(min = 8, max = 64, message = "A senha deve ter entre 8 e 64 caracteres")
    @Pattern(regexp = "^\\S+$", message = "A senha não deve conter espaços")
    String password
) {
    public UserSignUpDTO {
        username = username == null ? null : username.trim().toLowerCase();
        role = role == null ? null : role.trim().toLowerCase();
    }
}
