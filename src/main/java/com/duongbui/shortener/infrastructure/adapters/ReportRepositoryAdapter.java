package com.duongbui.shortener.infrastructure.adapters;

import com.duongbui.shortener.domain.report.Report;
import com.duongbui.shortener.domain.report.ReportRepository;
import com.duongbui.shortener.infrastructure.persistence.JpaAnalyticRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class ReportRepositoryAdapter implements ReportRepository {
    private final JpaAnalyticRepository repository;

    public ReportRepositoryAdapter(JpaAnalyticRepository repository) {
        this.repository = repository;
    }

    @Override
    public Mono<List<Report>> find(String code) {
        return Mono.justOrEmpty(repository.findReportsByCode(code))
                .flatMapIterable(list -> list)
                .mapNotNull(projection ->
                        Report.builder()
                                .code(projection.getCode())
                                .userAgent(projection.getUserAgent())
                                .hitCount(projection.getHitCount())
                                .build()
                )
                .collectList();
    }
}
