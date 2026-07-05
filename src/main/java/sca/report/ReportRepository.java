package sca.report;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReportRepository extends JpaRepository<Report, UUID> {

    @Query("""
            SELECT r.food.id, r.food.name, r.food.price,
                   SUM(r.receivedKg), SUM(r.wastedKg)
            FROM Report r
            WHERE r.createdAt >= :since
            GROUP BY r.food.id, r.food.name, r.food.price
            """)
    List<Object[]> findSummaryGroupedByFood(@Param("since") Instant since);

    @Query("""
            SELECT r.school.username, SUM(r.receivedKg), SUM(r.wastedKg), r.food.price
            FROM Report r
            WHERE r.food.id = :foodId AND r.createdAt >= :since
            GROUP BY r.school.id, r.school.username, r.food.price
            """)
    List<Object[]> findSummaryBySchoolForFood(
            @Param("foodId") UUID foodId,
            @Param("since") Instant since);
}
