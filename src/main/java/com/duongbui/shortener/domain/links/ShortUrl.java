package com.duongbui.shortener.domain.links;

import lombok.Getter;
import org.jmolecules.ddd.annotation.ValueObject;

@Getter
@ValueObject
public final class ShortUrl {
    private final String value;

    public ShortUrl(String domain, ShortenLinkCode code) {
        this.value = domain.concat("/").concat(code.getValue());
    }

    public ShortUrl(String value) {
        this.value = value;
    }
}
