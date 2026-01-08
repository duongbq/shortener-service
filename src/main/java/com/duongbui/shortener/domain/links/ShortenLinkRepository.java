package com.duongbui.shortener.domain.links;

import reactor.core.publisher.Mono;

public interface ShortenLinkRepository {
    Mono<ShortenLink> save(ShortenLink shortenLink);
    Mono<ShortenLink> findByOriginalUrl(String url);
    Mono<ShortenLink> findByCode(String code);
}
