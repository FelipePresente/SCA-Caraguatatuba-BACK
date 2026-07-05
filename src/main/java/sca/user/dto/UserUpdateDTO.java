package sca.user.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserUpdateDTO(
    @NotNull
    UUID id,

    @Size(min = 4, max = 16, message = "O nome de usuário deve ter entre 4 e 16 caracteres")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "O nome de usuário deve conter apenas letras e números, sem espaços")
    String newUsername,

    @Size(min = 8, max = 64, message = "A senha deve ter entre 8 e 64 caracteres")
    @Pattern(regexp = "^\\S+$", message = "A senha não deve conter espaços")
    String newPassword
) {
    public UserUpdateDTO {
        newUsername = newUsername == null ? null : newUsername.toLowerCase().trim();
    }
}
