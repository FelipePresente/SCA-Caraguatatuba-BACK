package sca.food;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import sca.exception.ConflictException;
import sca.food.dto.FoodCreationDTO;
import sca.food.dto.FoodResponseDTO;

@Service
public class FoodService {
    private final FoodRepository foodRepository;

    public FoodService(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    public List<FoodResponseDTO> findAll() {
        return this.foodRepository.findAll()
                .stream()
                .map(FoodResponseDTO::fromEntity)
                .toList();
    }

    public Void create(FoodCreationDTO data) {
        Food food = this.foodRepository.findByName(data.name());

        if (food != null) {
            throw new ConflictException("Alimento já existe");
        }

        this.foodRepository.save(new Food(data.name(), data.price()));

        return null;
    }

    public Void delete(UUID id) {
        this.foodRepository.deleteById(id);

        return null;
    }
}
