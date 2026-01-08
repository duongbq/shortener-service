package com.duongbui.shortener.infrastructure.adapters;

import com.duongbui.shortener.domain.analytics.Analytic;
import com.duongbui.shortener.infrastructure.persistence.JpaAnalytic;
import com.duongbui.shortener.infrastructure.persistence.JpaAnalyticRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static com.duongbui.shortener.infrastructure.messaging.DeclarationConfig.QUEUE_ANALYTIC;

@Slf4j
@Component
public class AnalyticHandler {
    private final ObjectMapper objectMapper;
    private final JpaAnalyticRepository repository;

    public AnalyticHandler(ObjectMapper objectMapper, JpaAnalyticRepository repository) {
        this.objectMapper = objectMapper;
        this.repository = repository;
    }


    @RabbitListener(queues = QUEUE_ANALYTIC, ackMode = "MANUAL")
    public void handle(Message message, Channel channel) throws IOException {
        log.info("[AnalyticHandler] Received message {}", message);
        try {
            var body = new String(message.getBody(), StandardCharsets.UTF_8);
            Analytic analytic = objectMapper.readValue(body, new TypeReference<>() {
            });
            var code = analytic.getCode();
            var userAgent = analytic.getUserAgent();
            var clientIp = analytic.getClientIp();
            var statistic = repository.findFirstByCodeAndUserAgentAndClientIp(code, userAgent, clientIp);

            statistic.ifPresentOrElse(
                    entity -> {
                        var hitCount = entity.getHitCount();
                        entity.setHitCount(hitCount + 1);
                        repository.save(entity);
                    },
                    () -> {
                        var entity = JpaAnalytic.builder().code(code).userAgent(userAgent).clientIp(clientIp).hitCount(1).version(1).build();
                        repository.save(entity);
                    }
            );
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            // Message failed — requeue it or send it to a dead-letter queue
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
        }
    }
}
