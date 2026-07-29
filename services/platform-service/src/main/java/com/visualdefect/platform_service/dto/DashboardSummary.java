package com.visualdefect.platform_service.dto;

public record DashboardSummary(long totalReports, long anomalyReports, double averageLatencyMs) {
}
