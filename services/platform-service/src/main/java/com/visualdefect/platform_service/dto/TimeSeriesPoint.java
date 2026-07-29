package com.visualdefect.platform_service.dto;

public class TimeSeriesPoint {

    private String timestamp;
    private double temperature;
    private double tension;
    private double speed;
    private double rollingForce;

    public TimeSeriesPoint() {
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getTension() {
        return tension;
    }

    public void setTension(double tension) {
        this.tension = tension;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getRollingForce() {
        return rollingForce;
    }

    public void setRollingForce(double rollingForce) {
        this.rollingForce = rollingForce;
    }
}