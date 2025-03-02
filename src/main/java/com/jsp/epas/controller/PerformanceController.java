package com.jsp.epas.controller;

import com.jsp.epas.service.PerformanceService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@AllArgsConstructor
public class PerformanceController {

    private final PerformanceService performanceService;

    @GetMapping("/performance/bell-curve")
    public ResponseEntity<Map<String, Object>> getPerformanceMetrics() {
        Map<String, Object> metrics = performanceService.calculatePerformanceMetrics();
        return ResponseEntity.ok(metrics);
    }
}