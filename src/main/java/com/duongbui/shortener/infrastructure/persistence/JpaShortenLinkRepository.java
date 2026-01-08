package com.duongbui.shortener.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaShortenLinkRepository extends JpaRepository<JpaShortenLink, String> {
    Optional<JpaShortenLink> findByOriginalUrl(String originalUrl);
    Optional<JpaShortenLink> findByCode(String code);
}
