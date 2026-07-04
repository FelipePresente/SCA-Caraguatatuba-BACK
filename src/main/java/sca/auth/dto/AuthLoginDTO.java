package sca.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthLoginDTO(
    @NotBlank(message = "Username must not be blank")
    String username,

    @NotBlank(message = "Password must not be blank")
    String password
) {
    public AuthLoginDTO {
        username = username == null ? null : username.toLowerCase().trim();
    }
}
