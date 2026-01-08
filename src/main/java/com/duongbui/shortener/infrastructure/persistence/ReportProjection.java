package com.duongbui.shortener.infrastructure.persistence;

public interface ReportProjection {
    String getCode();
    String getUserAgent();
    int getHitCount();
}
