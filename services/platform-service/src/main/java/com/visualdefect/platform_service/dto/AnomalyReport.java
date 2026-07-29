package com.visualdefect.platform_service.dto;

public class AnomalyReport {

    private String reportId;
    private String streamId;
    private String modality;
    private boolean anomaly;
    private String anomalyType;
    private double anomalyScore;
    private String severity;
    private BoundingBox location;
    private String modelName;
    private String modelVersion;
    private long latencyMs;
    private String metric;
    private String startTime;
    private String endTime;
    private String fileName;

    public AnomalyReport() {
    }

    public AnomalyReport(
            String reportId,
            String streamId,
            String modality,
            boolean anomaly,
            String anomalyType,
            double anomalyScore,
            String severity,
            BoundingBox location,
            String modelName,
            String modelVersion,
            long latencyMs
    ) {
        this.reportId = reportId;
        this.streamId = streamId;
        this.modality = modality;
        this.anomaly = anomaly;
        this.anomalyType = anomalyType;
        this.anomalyScore = anomalyScore;
        this.severity = severity;
        this.location = location;
        this.modelName = modelName;
        this.modelVersion = modelVersion;
        this.latencyMs = latencyMs;
    }

    public String getReportId() {
        return reportId;
    }

    public String getStreamId() {
        return streamId;
    }

    public String getModality() {
        return modality;
    }

    public boolean isAnomaly() {
        return anomaly;
    }

    public String getAnomalyType() {
        return anomalyType;
    }

    public double getAnomalyScore() {
        return anomalyScore;
    }

    public String getSeverity() {
        return severity;
    }

    public BoundingBox getLocation() {
        return location;
    }

    public String getModelName() {
        return modelName;
    }

    public String getModelVersion() {
        return modelVersion;
    }

    public long getLatencyMs() {
        return latencyMs;
    }
    public String getMetric() {
        return metric;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }
    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }
    public void setMetric(String metric) {
        this.metric = metric;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
