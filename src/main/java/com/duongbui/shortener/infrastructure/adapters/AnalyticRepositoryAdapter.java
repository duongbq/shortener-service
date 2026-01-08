package com.duongbui.shortener.infrastructure.adapters;

import com.duongbui.shortener.domain.analytics.Analytic;
import com.duongbui.shortener.domain.analytics.AnalyticRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static com.duongbui.shortener.infrastructure.messaging.DeclarationConfig.EXCHANGE_ANALYTIC;
import static com.duongbui.shortener.infrastructure.messaging.DeclarationConfig.ROUTING_ANALYTIC;

@Slf4j
@Service
public class AnalyticRepositoryAdapter implements AnalyticRepository {
    private final RabbitTemplate rabbitTemplate;

    public AnalyticRepositoryAdapter(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public Mono<Void> delegate(Analytic analytic) {
        return Mono.just(analytic)
                .doOnNext(message -> rabbitTemplate.convertAndSend(EXCHANGE_ANALYTIC, ROUTING_ANALYTIC, message))
                .then();
    }
}
