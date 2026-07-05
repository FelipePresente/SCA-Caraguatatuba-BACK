package sca.report.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ReportCreationDTO(
    @NotNull(message = "Food ID must not be null")
    UUID foodId,

    @NotNull(message = "Received kg must not be null")
    @Positive(message = "Received kg must be positive")
    Double receivedKg,

    @NotNull(message = "Wasted kg must not be null")
    @Positive(message = "Wasted kg must be positive")
    Double wastedKg
) {
}
