package com.duongbui.shortener.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "analytics")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JpaAnalytic {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String code;
    @Column(name = "user_agent")
    private String userAgent;
    @Column(name = "client_ip")
    private String clientIp;
    @Column(name = "hit_count")
    private Integer hitCount;
    @Version
    private Integer version;
}
