package com.visualdefect.platform_service.controller;

import com.visualdefect.platform_service.dto.AnomalyReport;
import com.visualdefect.platform_service.dto.VisualDetectionRequest;
import com.visualdefect.platform_service.service.VisualDetectionService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/detections")
public class VisualDetectionController {

    private final VisualDetectionService visualDetectionService;

    public VisualDetectionController(
            VisualDetectionService visualDetectionService
    ) {
        this.visualDetectionService = visualDetectionService;
    }

    @PostMapping("/visual")
    public AnomalyReport detectVisual(
            @RequestBody VisualDetectionRequest request
    ) {
        return visualDetectionService.detect(request);
    }
}