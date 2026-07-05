package sca.report;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import sca.report.dto.FoodSummaryDTO;
import sca.report.dto.ReportCreationDTO;
import sca.report.dto.SchoolFoodSummaryDTO;
import sca.user.User;

@RestController
@RequestMapping("reports")
public class ReportController {
    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/summary")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SEDUC_USER')")
    @ResponseStatus(HttpStatus.OK)
    public List<FoodSummaryDTO> getSummary() {
        return this.reportService.getSummary();
    }

    @GetMapping("/summary/{foodId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SEDUC_USER')")
    @ResponseStatus(HttpStatus.OK)
    public List<SchoolFoodSummaryDTO> getSummaryByFood(@PathVariable("foodId") UUID foodId) {
        return this.reportService.getSummaryByFood(foodId);
    }

    @PostMapping
    @PreAuthorize("hasRole('SCHOOL')")
    @ResponseStatus(HttpStatus.CREATED)
    public Void create(@Valid @RequestBody ReportCreationDTO data, @AuthenticationPrincipal User school) {
        return this.reportService.create(data, school);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SEDUC_USER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Void delete(@PathVariable("id") UUID id) {
        return this.reportService.delete(id);
    }
}
