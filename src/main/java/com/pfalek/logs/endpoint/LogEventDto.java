package com.pfalek.logs.endpoint;

import com.pfalek.logs.model.Application;
import com.pfalek.logs.model.LogEvent;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Builder
@Value
public class LogEventDto {

    private final LocalDate date;
    private final String level;
    private final String clas;
    private final String message;
    private final Application application;

    public static LogEventDto from(final LogEvent logEvent) {
        return LogEventDto.builder()
                .date(logEvent.getDate())
                .level(logEvent.getLevel())
                .clas(logEvent.getClas())
                .message(logEvent.getMessage())
                .application(logEvent.getApplication())
                .build();
    }
}
