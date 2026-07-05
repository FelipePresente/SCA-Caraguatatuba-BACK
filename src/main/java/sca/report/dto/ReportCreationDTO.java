package sca.report.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ReportCreationDTO(
    @NotNull(message = "O ID do alimento não deve ser nulo")
    UUID foodId,

    @NotNull(message = "A quantidade recebida (kg) não deve ser nula")
    @Positive(message = "A quantidade recebida (kg) deve ser positiva")
    Double receivedKg,

    @NotNull(message = "A quantidade desperdiçada (kg) não deve ser nula")
    @Positive(message = "A quantidade desperdiçada (kg) deve ser positiva")
    Double wastedKg
) {
}
