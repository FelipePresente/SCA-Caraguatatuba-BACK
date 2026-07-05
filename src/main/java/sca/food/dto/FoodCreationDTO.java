package sca.food.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record FoodCreationDTO(
    @NotBlank(message = "O nome não deve estar em branco")
    @Size(min = 4, max = 16, message = "O nome deve ter entre 4 e 16 caracteres")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "O nome deve conter apenas letras e números, sem espaços")
    String name,

    @NotNull(message = "O preço não deve ser nulo")
    @Positive(message = "O preço deve ser maior que zero")
    Double price
) {
    public FoodCreationDTO {
        name = name == null ? null : name.trim().toLowerCase();
    }
}
