package com.visualdefect.platform_service.controller;

import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/services")
public class ServiceStatusController {
    @GetMapping("/status")
    public Map<String, Object> status() {
        return Map.of("platformService", "UP", "visualDetector", "MOCK", "timeSeriesDetector", "MOCK");
    }
}
