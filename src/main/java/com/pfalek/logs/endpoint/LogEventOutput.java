package com.pfalek.logs.endpoint;

import com.pfalek.logs.model.LogEvent;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Builder
@Value
public class LogEventOutput {

    private final LocalDateTime date;
    private final String level;
    private final String clas;
    private final String message;

    public static LogEventOutput from(final LogEvent logEvent) {
        return LogEventOutput.builder()
                .date(logEvent.getDateTime())
                .level(logEvent.getLevel())
                .clas(logEvent.getClas())
                .message(logEvent.getMessage())
                .build();
    }
}
