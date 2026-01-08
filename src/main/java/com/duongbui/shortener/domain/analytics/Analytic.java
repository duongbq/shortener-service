package com.duongbui.shortener.domain.analytics;

import lombok.Builder;
import lombok.Getter;
import org.jmolecules.ddd.annotation.AggregateRoot;
import org.jmolecules.ddd.annotation.Entity;
import org.jmolecules.ddd.annotation.Identity;

@Builder
@Getter
@Entity
@AggregateRoot
public final class Analytic {
    @Identity
    private String code;
    private String userAgent;
    private String clientIp;
    private int hitCount;
}
