package sca.report;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import sca.exception.ResourceNotFoundException;
import sca.food.Food;
import sca.food.FoodRepository;
import sca.report.dto.FoodSummaryDTO;
import sca.report.dto.ReportCreationDTO;
import sca.report.dto.SchoolFoodSummaryDTO;
import sca.user.User;

@Service
public class ReportService {
    private final ReportRepository reportRepository;
    private final FoodRepository foodRepository;

    public ReportService(ReportRepository reportRepository, FoodRepository foodRepository) {
        this.reportRepository = reportRepository;
        this.foodRepository = foodRepository;
    }

    public List<FoodSummaryDTO> getSummary() {
        Instant since = Instant.now().minus(30, ChronoUnit.DAYS);
        List<Object[]> rows = this.reportRepository.findSummaryGroupedByFood(since);

        return rows.stream().map(row -> {
            UUID foodId = row[0] instanceof UUID id ? id : UUID.fromString(row[0].toString());
            String foodName = (String) row[1];
            Double price = ((Number) row[2]).doubleValue();
            Double totalSentKg = ((Number) row[3]).doubleValue();
            Double totalWastedKg = ((Number) row[4]).doubleValue();
            Double moneySpent = totalSentKg * price;
            Double moneyLost = totalWastedKg * price;
            Double wastePercentage = totalSentKg > 0 ? (totalWastedKg / totalSentKg) * 100.0 : 0.0;

            return new FoodSummaryDTO(foodId, foodName, totalSentKg, totalWastedKg, moneySpent, moneyLost, wastePercentage);
        }).toList();
    }

    public List<SchoolFoodSummaryDTO> getSummaryByFood(UUID foodId) {
        Instant since = Instant.now().minus(30, ChronoUnit.DAYS);
        List<Object[]> rows = this.reportRepository.findSummaryBySchoolForFood(foodId, since);

        return rows.stream().map(row -> {
            String schoolUsername = (String) row[0];
            Double totalSentKg = ((Number) row[1]).doubleValue();
            Double totalWastedKg = ((Number) row[2]).doubleValue();
            Double price = ((Number) row[3]).doubleValue();
            Double moneySpent = totalSentKg * price;
            Double moneyLost = totalWastedKg * price;
            Double wastePercentage = totalSentKg > 0 ? (totalWastedKg / totalSentKg) * 100.0 : 0.0;

            return new SchoolFoodSummaryDTO(schoolUsername, totalSentKg, totalWastedKg, moneySpent, moneyLost, wastePercentage);
        }).toList();
    }

    public Void create(ReportCreationDTO data, User school) {
        Food food = this.foodRepository.findById(data.foodId())
                .orElseThrow(() -> new ResourceNotFoundException("Food not found"));

        Report newReport = new Report(school, food, data.receivedKg(), data.wastedKg());

        this.reportRepository.save(newReport);

        return null;
    }

    public Void delete(UUID id) {
        Report report = this.reportRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Report not found"));

        this.reportRepository.delete(report);

        return null;
    }
}
