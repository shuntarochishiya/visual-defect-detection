package com.visualdefect.platform_service.controller;

import com.visualdefect.platform_service.dto.AnomalyReport;
import com.visualdefect.platform_service.dto.DashboardSummary;
import com.visualdefect.platform_service.service.ReportStore;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/dashboard")
public class DashboardController {
    private final ReportStore reportStore;

    public DashboardController(ReportStore reportStore) {
        this.reportStore = reportStore;
    }

    @GetMapping("/summary")
    public DashboardSummary summary() {
        List<AnomalyReport> reports = reportStore.findAll();
        long anomalies = reports.stream().filter(AnomalyReport::isAnomaly).count();
        double latency = reports.stream().mapToLong(AnomalyReport::getLatencyMs).average().orElse(0);
        return new DashboardSummary(reports.size(), anomalies, latency);
    }
}
