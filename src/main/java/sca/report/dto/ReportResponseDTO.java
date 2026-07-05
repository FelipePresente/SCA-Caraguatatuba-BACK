package sca.report.dto;

import java.time.Instant;
import java.util.UUID;

import sca.report.Report;

public record ReportResponseDTO(
    UUID id,
    String schoolUsername,
    String foodName,
    Double receivedKg,
    Double wastedKg,
    Instant createdAt
) {
    public static ReportResponseDTO fromEntity(Report report) {
        return new ReportResponseDTO(
            report.getId(),
            report.getSchool().getUsername(),
            report.getFood().getName(),
            report.getReceivedKg(),
            report.getWastedKg(),
            report.getCreatedAt()
        );
    }
}
