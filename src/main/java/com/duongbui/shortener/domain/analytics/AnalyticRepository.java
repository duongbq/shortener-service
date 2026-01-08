package com.duongbui.shortener.domain.analytics;

import reactor.core.publisher.Mono;

public interface AnalyticRepository {
    Mono<Void> delegate(Analytic analytic);
}
