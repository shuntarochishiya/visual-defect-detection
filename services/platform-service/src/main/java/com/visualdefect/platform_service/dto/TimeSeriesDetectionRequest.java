package com.visualdefect.platform_service.dto;

import java.util.List;

public class TimeSeriesDetectionRequest {

    private String streamId;
    private List<TimeSeriesPoint> points;

    public TimeSeriesDetectionRequest() {
    }

    public String getStreamId() {
        return streamId;
    }

    public void setStreamId(String streamId) {
        this.streamId = streamId;
    }

    public List<TimeSeriesPoint> getPoints() {
        return points;
    }

    public void setPoints(List<TimeSeriesPoint> points) {
        this.points = points;
    }
}