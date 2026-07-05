package sca.report.dto;

import java.util.UUID;

public record FoodSummaryDTO(
    UUID foodId,
    String foodName,
    Double totalSentKg,
    Double totalWastedKg,
    Double moneySpent,
    Double moneyLost,
    Double wastePercentage
) {
}
