package sca.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthLoginDTO(
    @NotBlank(message = "O nome de usuário não deve estar em branco")
    String username,

    @NotBlank(message = "A senha não deve estar em branco")
    String password
) {
    public AuthLoginDTO {
        username = username == null ? null : username.toLowerCase().trim();
    }
}
