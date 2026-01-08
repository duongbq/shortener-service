package com.duongbui.shortener.infrastructure.caching;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@EnableCaching
public class CachingConfig {
    public static final String LINK_CACHE = "LINK_CACHE";
    private final List<ConcurrentMapCache> CACHES = List.of(
            new ConcurrentMapCache(LINK_CACHE)
            // Add here if more is needed
    );

    @Bean
    public CacheManager cacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(CACHES);
        return cacheManager;
    }
}
