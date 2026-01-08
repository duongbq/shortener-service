package com.duongbui.shortener.domain.analytics;

import reactor.core.publisher.Mono;

public interface StatisticizeUseCase {
    Mono<Void> analyze(Analytic analytic);
}
