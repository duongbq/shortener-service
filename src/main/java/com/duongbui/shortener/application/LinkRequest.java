package com.duongbui.shortener.application;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LinkRequest {
    @NotBlank
    private String originalUrl;
    private String alias;
}
