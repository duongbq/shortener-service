package com.duongbui.shortener.domain.links;

import reactor.core.publisher.Mono;

public interface ShortenLinkRetrievalUseCase {
    Mono<ShortenLink>  retrieve(String code);
}
