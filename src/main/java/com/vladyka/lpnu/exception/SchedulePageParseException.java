package com.vladyka.lpnu.exception;

public class SchedulePageParseException extends RuntimeException {

    private String url;

    public SchedulePageParseException(String url, String message) {
        super(message);
        this.url = url;
    }

    public SchedulePageParseException(String message) {
        super(message);
    }

    public String getUrl() {
        return url;
    }
}
