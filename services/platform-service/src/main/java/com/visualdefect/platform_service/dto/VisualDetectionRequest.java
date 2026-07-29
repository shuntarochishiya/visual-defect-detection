package com.visualdefect.platform_service.dto;

public class VisualDetectionRequest {

    private String streamId;
    private String fileName;
    private String timestamp;

    public VisualDetectionRequest() {
    }

    public String getStreamId() {
        return streamId;
    }

    public void setStreamId(String streamId) {
        this.streamId = streamId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}