package com.duongbui.shortener.domain.report;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class ReportUseCaseAdapter implements ReportUseCase {
    private final ReportRepository repository;

    public ReportUseCaseAdapter(ReportRepository repository) {
        this.repository = repository;
    }

    @Override
    public Flux<Report> report(String code) {
        return repository.find(code).flatMapIterable(list -> list);
    }
}
