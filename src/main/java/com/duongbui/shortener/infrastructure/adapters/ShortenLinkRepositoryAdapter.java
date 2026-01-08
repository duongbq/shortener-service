package com.duongbui.shortener.infrastructure.adapters;

import com.duongbui.shortener.domain.links.*;
import com.duongbui.shortener.infrastructure.persistence.JpaShortenLink;
import com.duongbui.shortener.infrastructure.persistence.JpaShortenLinkRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static com.duongbui.shortener.infrastructure.caching.CachingConfig.LINK_CACHE;

@Slf4j
@Service
public class ShortenLinkRepositoryAdapter implements ShortenLinkRepository {
    private final JpaShortenLinkRepository repository;

    public ShortenLinkRepositoryAdapter(JpaShortenLinkRepository repository) {
        this.repository = repository;
    }

    @Override
    @CachePut(value = LINK_CACHE) // @CachePut will actually run the method and then put its results in the cache
    public Mono<ShortenLink> save(ShortenLink shortenLink) {
        return Mono.just(shortenLink)
                .doOnNext(entity -> {
                    log.debug("Saving ShortenLink {}", entity);
                    repository.save(
                            JpaShortenLink.builder()
                                    .code(entity.getCode().getValue())
                                    .originalUrl(entity.getOriginalUrl())
                                    .shortUrl(entity.getShortUrl().getValue())
                                    .aliasUrl(entity.getAliasUrl().getValue())
                                    .creator(entity.getCreator())
                                    .build()
                    );
                });
    }

    @Override
    @Cacheable(value = LINK_CACHE) // @Cacheable will skip the next running of the method
    @CacheEvict(value = LINK_CACHE, allEntries = true)
    public Mono<ShortenLink> findByOriginalUrl(String url) {
        log.debug("Finding in database by original url {}", url);
        return Mono.justOrEmpty(repository.findByOriginalUrl(url)).map(this::map);
    }

    @Override
    @Cacheable(value = LINK_CACHE)
    public Mono<ShortenLink> findByCode(String code) {
        log.debug("Finding in database by code {}", code);
        return Mono.justOrEmpty(repository.findByCode(code)).map(this::map);
    }

    private ShortenLink map(JpaShortenLink entity) {
        return ShortenLink.builder()
                .code(new ShortenLinkCode(entity.getCode()))
                .originalUrl(entity.getOriginalUrl())
                .shortUrl(new ShortUrl(entity.getShortUrl()))
                .aliasUrl(new AliasUrl(entity.getAliasUrl()))
                .creator(entity.getCreator())
                .build();
    }
}
