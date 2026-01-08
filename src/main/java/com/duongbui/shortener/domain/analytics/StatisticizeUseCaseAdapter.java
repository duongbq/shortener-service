package com.duongbui.shortener.domain.analytics;

import com.duongbui.shortener.domain.links.ShortenLink;
import com.duongbui.shortener.domain.links.ShortenLinkRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
public class StatisticizeUseCaseAdapter implements StatisticizeUseCase {
    private final ShortenLinkRepository shortenLinkRepository;
    private final AnalyticRepository analyticRepository;

    public StatisticizeUseCaseAdapter(ShortenLinkRepository shortenLinkRepository, AnalyticRepository analyticRepository) {
        this.shortenLinkRepository = shortenLinkRepository;
        this.analyticRepository = analyticRepository;
    }


    @Override
    public Mono<Void> analyze(Analytic analytic) {
        return shortenLinkRepository.findByCode(analytic.getCode()) // No worries! This method is cacheable
                .defaultIfEmpty(ShortenLink.builder().build())
                .flatMap(link ->
                        Objects.isNull(link.getCode()) ? Mono.empty() : analyticRepository.delegate(analytic)
                );
    }
}
