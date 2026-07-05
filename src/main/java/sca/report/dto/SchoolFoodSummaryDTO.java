package sca.report.dto;

public record SchoolFoodSummaryDTO(
    String schoolUsername,
    Double totalSentKg,
    Double totalWastedKg,
    Double moneySpent,
    Double moneyLost,
    Double wastePercentage
) {
}
