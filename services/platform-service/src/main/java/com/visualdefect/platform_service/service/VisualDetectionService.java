package com.visualdefect.platform_service.service;

import com.visualdefect.platform_service.dto.AnomalyReport;
import com.visualdefect.platform_service.dto.BoundingBox;
import com.visualdefect.platform_service.dto.VisualDetectionRequest;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class VisualDetectionService {
    private final ReportStore reportStore;

    public VisualDetectionService(ReportStore reportStore) {
        this.reportStore = reportStore;
    }

    public AnomalyReport detect(VisualDetectionRequest request) {
        BoundingBox box = new BoundingBox(
                120,
                65,
                180,
                90
        );

        AnomalyReport report = new AnomalyReport(
                UUID.randomUUID().toString(),
                request.getStreamId(),
                "VISUAL",
                true,
                "SCRATCH",
                0.93,
                "HIGH",
                box,
                "mock-yolo",
                "v1",
                37
        );
        report.setFileName(request.getFileName());
        reportStore.save(report);
        return report;
    }
}
