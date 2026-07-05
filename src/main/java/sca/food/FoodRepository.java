package sca.food;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodRepository extends JpaRepository<Food, UUID> {
    Food findByName(String name);
}
