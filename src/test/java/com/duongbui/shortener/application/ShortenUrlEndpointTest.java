package com.duongbui.shortener.application;

import com.duongbui.shortener.domain.analytics.StatisticizeUseCase;
import com.duongbui.shortener.domain.links.*;
import com.duongbui.shortener.infrastructure.security.TestSecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockJwt;

@WebFluxTest(controllers = ShortenUrlEndpoint.class)
@Import(TestSecurityConfig.class)
@MockitoBean(types = {
        StatisticizeUseCase.class
})
class ShortenUrlEndpointTest {
    private static final String PATH = "/links";
    @MockitoBean
    private ShortenLinkUseCase useCase;
    @Autowired
    private WebTestClient authorizedClient;

    @Test
    void givenInvalidRequest_whenAccessShortenUrlEndpoint_thenBadRequest() {
        authorizedClient
                .mutateWith(mockJwt().jwt(jwt -> jwt.subject("testuser")))
                .post()
                .uri(PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(LinkRequest.class)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void givenValidRequest_whenAccessShortenUrlEndpoint_thenOk() {
        when(useCase.shorten("testuser", "long", null))
                .thenReturn(Mono.just(
                        ShortenLink.builder().code(new ShortenLinkCode("1"))
                                .originalUrl("long").shortUrl(new ShortUrl("short")).aliasUrl(new AliasUrl(null)).creator("testuser").build()
                ));
        authorizedClient
                .mutateWith(mockJwt().jwt(jwt -> jwt.subject("testuser")))
                .post()
                .uri(PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(LinkRequest.builder().originalUrl("long").build())
                .exchange()
                .expectStatus().isOk();
        ;
    }


}
