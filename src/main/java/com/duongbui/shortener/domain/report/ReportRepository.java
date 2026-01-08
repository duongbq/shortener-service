package com.duongbui.shortener.domain.report;

import reactor.core.publisher.Mono;

import java.util.List;

public interface ReportRepository {
    Mono<List<Report>> find(String code);
}
