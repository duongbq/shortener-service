package com.duongbui.shortener.domain.links;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
public class ShortenLinkRetrievalUseCaseAdapter implements ShortenLinkRetrievalUseCase {
    private final ShortenLinkRepository repository;
    public ShortenLinkRetrievalUseCaseAdapter(ShortenLinkRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<ShortenLink> retrieve(String code) {
        return repository.findByCode(code);
    }
}
