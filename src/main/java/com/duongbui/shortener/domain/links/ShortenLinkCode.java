package com.duongbui.shortener.domain.links;

import lombok.Getter;
import org.apache.commons.lang3.RandomStringUtils;
import org.jmolecules.ddd.annotation.ValueObject;

import java.util.Base64;
import java.util.UUID;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Objects.requireNonNullElse;

/**
 * As soon as the code is generated, it will never change, so it should be immutable
 */
@Getter
@ValueObject
public final class ShortenLinkCode {
    private static final int CODE_LENGTH = 8;
    private static final String DELIMITER = RandomStringUtils.secureStrong().nextAlphanumeric(9);
    private static final Base64.Encoder ENCODER = Base64.getEncoder().withoutPadding();
    private final String value;

    public ShortenLinkCode(String originalUrl, String domain, String alias) {
        var characters = String.join(
                        DELIMITER,
                        ENCODER.encodeToString(originalUrl.getBytes(UTF_8)),
                        ENCODER.encodeToString(domain.getBytes(UTF_8)),
                        ENCODER.encodeToString(requireNonNullElse(alias, UUID.randomUUID().toString()).getBytes(UTF_8))
                );
        var builder = new StringBuilder();
        for (var i = 0; i < CODE_LENGTH; i++) {
            builder.append(RandomStringUtils.secureStrong().next(1, 0, 0, true, true, characters.toCharArray()));
        }
        this.value = builder.toString();
    }

    public ShortenLinkCode(String value) {
        this.value = value;
    }
}
