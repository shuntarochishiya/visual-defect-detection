package com.visualdefect.platform_service.service;

import com.visualdefect.platform_service.dto.AnomalyReport;
import com.visualdefect.platform_service.dto.TimeSeriesDetectionRequest;
import com.visualdefect.platform_service.dto.TimeSeriesPoint;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TimeSeriesDetectionService {

    public AnomalyReport detect(TimeSeriesDetectionRequest request) {
        List<TimeSeriesPoint> points = request.getPoints();

        TimeSeriesPoint latest = points.get(points.size() - 1);

        AnomalyReport report = new AnomalyReport(
                UUID.randomUUID().toString(),
                request.getStreamId(),
                "TIME_SERIES",
                true,
                "SPIKE",
                0.87,
                "MEDIUM",
                null,
                "mock-z-score",
                "v1",
                12
        );

        report.setMetric("temperature");
        report.setStartTime(latest.getTimestamp());
        report.setEndTime(latest.getTimestamp());

        return report;
    }
}