package com.duongbui.shortener.application;

import com.duongbui.shortener.domain.analytics.Analytic;
import com.duongbui.shortener.domain.analytics.StatisticizeUseCase;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import org.springframework.web.util.pattern.PathPatternParser;
import reactor.core.publisher.Mono;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Optional;

import static org.springframework.http.HttpMethod.GET;

@Component
public class AnalyticFilter implements WebFilter {
    private final StatisticizeUseCase useCase;
    private final PathPatternParser pathPatternParser;

    public AnalyticFilter(StatisticizeUseCase useCase) {
        this.useCase = useCase;
        this.pathPatternParser = new PathPatternParser();
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        var request = exchange.getRequest();
        var pathWithinApplication = request.getPath().pathWithinApplication();
        var pathPattern = pathPatternParser.parse("/c/**");
        var method = request.getMethod();

        if (pathPattern.matches(pathWithinApplication) && GET == method) {
            var clientIp = Optional.ofNullable(request.getRemoteAddress())
                    .map(InetSocketAddress::getAddress)
                    .map(InetAddress::getHostAddress)
                    .orElse("unknown");
            var userAgent = request.getHeaders().getFirst(HttpHeaders.USER_AGENT);
            var code = StringUtils.substringAfter(request.getURI().getRawPath(), "/c/");
            var analytic = Analytic.builder().code(code).userAgent(userAgent).clientIp(clientIp).hitCount(1).build();
            return chain.filter(exchange).then(useCase.analyze(analytic));
        }
        return chain.filter(exchange);
    }
}
