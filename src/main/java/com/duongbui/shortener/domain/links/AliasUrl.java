package com.duongbui.shortener.domain.links;

import lombok.Getter;
import org.jmolecules.ddd.annotation.ValueObject;

@Getter
@ValueObject
public final class AliasUrl {
    private final String value;

    public AliasUrl(String value) {
        this.value = value;
    }

    public AliasUrl(String domain, String alias) {
        this.value = domain.concat("/").concat(alias);
    }
}
