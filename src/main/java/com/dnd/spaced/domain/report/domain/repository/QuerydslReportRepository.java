package com.dnd.spaced.domain.report.domain.repository;

import com.dnd.spaced.domain.report.domain.Report;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.dnd.spaced.domain.report.domain.QReport.report;

@Repository
@RequiredArgsConstructor
public class QuerydslReportRepository implements ReportRepository {

    private final JPAQueryFactory queryFactory;
    private final ReportCrudRepository reportCrudRepository;

    @Override
    public void save(Report report) {
        reportCrudRepository.save(report);
    }

    @Override
    public Optional<Report> findById(Long id) {
        return reportCrudRepository.findById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return reportCrudRepository.existsById(id);
    }

    @Override
    public void deleteById(Long id) {
        reportCrudRepository.deleteById(id);
    }

    @Override
    public List<Report> findReportsAfterId(Long lastReportId, int size) {
        return queryFactory.selectFrom(report)
                .where(lastReportId != null ? report.id.gt(lastReportId) : null)
                .orderBy(report.id.asc())
                .limit(size)
                .fetch();
    }

}
