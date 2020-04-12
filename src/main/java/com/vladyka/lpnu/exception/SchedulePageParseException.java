package com.vladyka.lpnu.exception;

public class SchedulePageParseException extends RuntimeException {

    public SchedulePageParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public SchedulePageParseException() {
        super();
    }

    public SchedulePageParseException(String message) {
        super(message);
    }
}
