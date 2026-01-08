package com.duongbui.shortener.domain.links;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
public class ShortenLinkUseCaseAdapter implements ShortenLinkUseCase {
    private final String domain;
    private final ShortenLinkRepository repository;

    public ShortenLinkUseCaseAdapter(@Value("${shortener.domain}") String domain, ShortenLinkRepository repository) {
        this.domain = domain;
        this.repository = repository;
    }

    @Override
    @Transactional
    public Mono<ShortenLink> shorten(String creator, String originalUrl, String alias) {
        return repository.findByOriginalUrl(originalUrl)
                .switchIfEmpty(
                        Mono.just(new ShortenLinkCode(originalUrl, domain, alias))
                                .map(code ->
                                        ShortenLink.builder()
                                                .code(code)
                                                .originalUrl(originalUrl)
                                                .shortUrl(new ShortUrl(domain, code))
                                                .aliasUrl(new AliasUrl(domain, StringUtils.defaultIfEmpty(alias, code.getValue())))
                                                .creator(creator)
                                                .build()
                                )
                                .flatMap(repository::save)
                );
    }

}
