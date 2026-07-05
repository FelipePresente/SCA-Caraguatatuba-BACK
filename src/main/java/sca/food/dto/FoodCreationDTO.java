package sca.food.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record FoodCreationDTO(
    @NotBlank(message = "Name must not be blank")
    @Size(min = 4, max = 16, message = "Name must be between 4 and 16 characters")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Name must only contain letters and numbers, without spaces")
    String name,

    @NotNull(message = "Price must not be null")
    @Positive(message = "Price must be greater than zero")
    Double price
) {
    public FoodCreationDTO {
        name = name == null ? null : name.trim().toLowerCase();
    }
}
