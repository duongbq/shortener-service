package com.duongbui.shortener.domain.links;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.jmolecules.ddd.annotation.AggregateRoot;
import org.jmolecules.ddd.annotation.Entity;
import org.jmolecules.ddd.annotation.Identity;

@Builder
@Getter
@Entity
@AggregateRoot
@AllArgsConstructor
public final class ShortenLink {
    @Identity
    private ShortenLinkCode code;
    private String originalUrl;
    private ShortUrl shortUrl;
    private AliasUrl aliasUrl;
    private String creator;
}
