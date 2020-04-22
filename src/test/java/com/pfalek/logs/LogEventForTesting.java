package com.pfalek.logs;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pfalek.logs.model.Application;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class LogEventForTesting {

    private LocalDateTime dateTime;
    private String level;
    private String clas;
    private String message;
    private Application application;

    public LogEventForTesting(@JsonProperty("dateTime") final LocalDateTime dateTime,
                              @JsonProperty("level") final String level,
                              @JsonProperty("clas") final String clas,
                              @JsonProperty("message") final String message,
                              @JsonProperty("application") final Application application) {
        this.dateTime = dateTime;
        this.level = level;
        this.clas = clas;
        this.message = message;
        this.application = application;
    }
}
