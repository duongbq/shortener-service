package com.duongbui.shortener.infrastructure.security;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import reactor.core.publisher.Mono;

@Configuration
public class KeycloakConfig {

    @Bean
    Converter<Jwt, Collection<GrantedAuthority>> keycloakGrantedAuthoritiesConverter(@Value("${spring.security.oauth2.client.registration.master-realm.client-id}") String clientId) {
        return new KeycloakGrantedAuthoritiesConverter(clientId);
    }

    @Bean
    Converter<Jwt, Mono<AbstractAuthenticationToken>> keycloakJwtAuthenticationConverter(Converter<Jwt, Collection<GrantedAuthority>> converter) {
        return new KeycloakJwtAuthenticationConverter(converter);
    }
}