package sca.report.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ReportCreationDTO(
    @NotNull(message = "O ID do alimento não deve ser nulo")
    UUID foodId,

    @Positive(message = "A quantidade recebida (kg) deve ser positiva")
    Double receivedKg,

    @Positive(message = "A quantidade desperdiçada (kg) deve ser positiva")
    Double wastedKg
) {
    public ReportCreationDTO {
        if (receivedKg == null && wastedKg == null) {
            throw new IllegalArgumentException("Deve ser fornecida a quantidade recebida ou a quantidade desperdiçada (ou ambas)");
        }
    }
}
