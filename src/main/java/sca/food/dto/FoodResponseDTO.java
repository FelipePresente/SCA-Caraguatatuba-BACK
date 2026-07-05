package sca.food.dto;

import java.util.UUID;

import sca.food.Food;

public record FoodResponseDTO(
    UUID id,
    String name,
    Double price
) {
    public static FoodResponseDTO fromEntity(Food food) {
        return new FoodResponseDTO(
            food.getId(),
            food.getName(),
            food.getPrice()
        );
    }
}
