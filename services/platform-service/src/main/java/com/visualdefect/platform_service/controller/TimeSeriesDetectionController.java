package com.visualdefect.platform_service.controller;

import com.visualdefect.platform_service.dto.AnomalyReport;
import com.visualdefect.platform_service.dto.TimeSeriesDetectionRequest;
import com.visualdefect.platform_service.service.TimeSeriesDetectionService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/detections")
public class TimeSeriesDetectionController {

    private final TimeSeriesDetectionService timeSeriesDetectionService;

    public TimeSeriesDetectionController(
            TimeSeriesDetectionService timeSeriesDetectionService
    ) {
        this.timeSeriesDetectionService = timeSeriesDetectionService;
    }

    @PostMapping("/time-series")
    public AnomalyReport detectTimeSeries(
            @RequestBody TimeSeriesDetectionRequest request
    ) {
        return timeSeriesDetectionService.detect(request);
    }
}