package com.duongbui.shortener.application;

import com.duongbui.shortener.domain.report.Report;
import com.duongbui.shortener.domain.report.ReportUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
public class AnalyticReportEndpoint {
    private final ReportUseCase useCase;

    public AnalyticReportEndpoint(ReportUseCase useCase) {
        this.useCase = useCase;
    }

    @GetMapping("/links")
    public Mono<ResponseEntity<List<Report>>> report(@RequestParam(required = false) String code, JwtAuthenticationToken auth) {
        return useCase.report(code).collectList().map(ResponseEntity::ok);
    }
}
