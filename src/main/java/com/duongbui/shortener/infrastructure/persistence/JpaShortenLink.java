package com.duongbui.shortener.infrastructure.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "shorten_links")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JpaShortenLink {
    @Id
    private String code;
    @Column(name = "original_url", length = 555)
    private String originalUrl;
    @Column(name = "short_url")
    private String shortUrl;
    @Column(name = "alias_url")
    private String aliasUrl;
    private String creator;
}
