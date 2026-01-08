package com.duongbui.shortener.domain.report;

import reactor.core.publisher.Flux;

public interface ReportUseCase {
    Flux<Report> report(String code);
}
