package com.visualdefect.platform_service.service;

import com.visualdefect.platform_service.dto.AnomalyReport;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ReportStore {
    private final ConcurrentHashMap<String, AnomalyReport> reports = new ConcurrentHashMap<>();

    public void save(AnomalyReport report) {
        reports.put(report.getReportId(), report);
    }

    public List<AnomalyReport> findAll() {
        List<AnomalyReport> result = new ArrayList<>(reports.values());
        result.sort(Comparator.comparing(AnomalyReport::getReportId).reversed());
        return result;
    }

    public AnomalyReport findById(String id) {
        return reports.get(id);
    }
}
