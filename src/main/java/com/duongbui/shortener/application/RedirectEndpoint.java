package com.duongbui.shortener.application;

import com.duongbui.shortener.domain.links.ShortenLinkRetrievalUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;

@RestController
public class RedirectEndpoint {
    private final ShortenLinkRetrievalUseCase useCase;

    public RedirectEndpoint(ShortenLinkRetrievalUseCase useCase) {
        this.useCase = useCase;
    }

    @GetMapping("/c/{code}")
    public Mono<Void> redirect(@PathVariable String code, ServerWebExchange exchange) {
        return useCase.retrieve(code)
                .switchIfEmpty(
                        Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"))
                )
                .flatMap(link -> {
                    var url = link.getOriginalUrl();
                    var response = exchange.getResponse();
                    response.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
                    response.getHeaders().setLocation(URI.create(url));
                    return exchange.getResponse().setComplete();
                });

    }
}
