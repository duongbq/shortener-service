package com.duongbui.shortener.domain.links;

import reactor.core.publisher.Mono;

public interface ShortenLinkUseCase {
    Mono<ShortenLink> shorten(String creator, String originalUrl, String alias);
}
