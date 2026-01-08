package com.duongbui.shortener.domain.links;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ShortenLinkUseCaseAdapterTest {
    private static final String CREATOR = "duongbui";
    private static final String DOMAIN = "https://duongbui.com";
    private static final String CODE = RandomStringUtils.secureStrong().nextAlphanumeric(10);
    private static final String ORIGINAL_LINK = "https://this-should-be-a-very-long-url.com?params=" + RandomStringUtils.secureStrong().nextAlphanumeric(10);
    private static final String ALIAS_URL = DOMAIN + "/alias";
    private static final String SHORT_URL = DOMAIN + "/" + CODE;
    @InjectMocks
    ShortenLinkUseCaseAdapter adapter;
    @Mock
    ShortenLinkRepository repository;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(adapter, "domain", DOMAIN);
    }

    @Test
    void givenExistingOriginalUrl_whenShorten_thenReturnFromDatabase() {
        var data = ShortenLink.builder()
                .code(new ShortenLinkCode(CODE))
                .originalUrl(ORIGINAL_LINK)
                .shortUrl(new ShortUrl(SHORT_URL))
                .aliasUrl(new AliasUrl(ALIAS_URL))
                .creator(CREATOR)
                .build();
        when(repository.findByOriginalUrl(ORIGINAL_LINK)).thenReturn(Mono.just(data));
        adapter.shorten(CREATOR, ORIGINAL_LINK, "alias")
                .as(StepVerifier::create).expectNext(data)
                .verifyComplete();
    }
}
