package com.duongbui.shortener.application;

import com.duongbui.shortener.domain.links.ShortenLink;
import com.duongbui.shortener.domain.links.ShortenLinkUseCase;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class ShortenUrlEndpoint {
    private final ShortenLinkUseCase useCase;

    public ShortenUrlEndpoint(ShortenLinkUseCase useCase) {
        this.useCase = useCase;
    }

    @PostMapping("/links")
    public Mono<ResponseEntity<ShortenLink>> shorten(@Valid @RequestBody Mono<LinkRequest> linkRequest, JwtAuthenticationToken auth) {
        return linkRequest.flatMap(request ->
                        useCase.shorten(auth.getToken().getSubject(), request.getOriginalUrl(), request.getAlias())
                ).map(ResponseEntity::ok);
    }
}
