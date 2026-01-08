package com.duongbui.shortener.infrastructure.messaging;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class DeclarationConfig {
    public static final String QUEUE_ANALYTIC = "queue.analytics";
    public static final String EXCHANGE_ANALYTIC = "exchange.analytics";
    public static final String ROUTING_ANALYTIC = "routing.analytics";
    private static final String BINDING_ANALYTIC = "binding.analytics";
    private static final int DEFAULT_TTL = 1000 * 15; // After 15 seconds, message will be dropped to DLQ

    @Bean(QUEUE_ANALYTIC)
    public Queue analyticsQueue() {
        Map<String, Object> properties = Map.of("x-message-ttl", DEFAULT_TTL);
        return new Queue(QUEUE_ANALYTIC, true, false, false, properties);
    }

    @Bean(EXCHANGE_ANALYTIC)
    public DirectExchange analyticsExchange() {
        return new DirectExchange(EXCHANGE_ANALYTIC);
    }

    @Bean(BINDING_ANALYTIC)
    public Binding analyticsBinding(@Qualifier(QUEUE_ANALYTIC) Queue queue, @Qualifier(EXCHANGE_ANALYTIC) DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_ANALYTIC);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter);
        return template;
    }
}
