package com.duongbui.shortener.domain.report;

import lombok.Builder;
import lombok.Getter;
import org.jmolecules.ddd.annotation.AggregateRoot;
import org.jmolecules.ddd.annotation.Entity;
import org.jmolecules.ddd.annotation.Identity;

@Builder
@Getter
@Entity
@AggregateRoot
public final class Report {
    @Identity
    private String code;
    private String userAgent;
    private int hitCount;
}
