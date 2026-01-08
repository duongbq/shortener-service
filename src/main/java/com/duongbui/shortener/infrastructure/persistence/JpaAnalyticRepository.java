package com.duongbui.shortener.infrastructure.persistence;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface JpaAnalyticRepository extends JpaRepository<JpaAnalytic, Long> {
    @Transactional
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<JpaAnalytic> findFirstByCodeAndUserAgentAndClientIp(String code, String userAgent, String clientIp);

    @Transactional(readOnly = true)
    @Query(
            """
                    SELECT a.code code, a.userAgent userAgent, SUM(a.hitCount) AS hitCount
                                FROM JpaAnalytic a WHERE a.code = COALESCE(?1, a.code)
                                            GROUP BY a.code, a.userAgent
                    """
    )
    List<ReportProjection> findReportsByCode(String code);
}
