package com.visualdefect.platform_service.controller;

import com.visualdefect.platform_service.dto.AnomalyReport;
import com.visualdefect.platform_service.service.ReportStore;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reports")
public class ReportController {
    private final ReportStore reportStore;

    public ReportController(ReportStore reportStore) {
        this.reportStore = reportStore;
    }

    @GetMapping
    public List<AnomalyReport> list() {
        return reportStore.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnomalyReport> get(@PathVariable String id) {
        AnomalyReport report = reportStore.findById(id);
        return report == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(report);
    }
}
