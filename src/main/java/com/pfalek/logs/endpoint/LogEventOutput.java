package com.pfalek.logs.endpoint;

import com.pfalek.logs.model.LogEvent;
import lombok.*;

import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDateTime;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class LogEventOutput {

    private LocalDateTime date;
    private String level;
    private String clas;
    private String message;

    public static LogEventOutput from(final LogEvent logEvent) {
        return LogEventOutput.builder()
                .date(logEvent.getDateTime())
                .level(logEvent.getLevel())
                .clas(logEvent.getClas())
                .message(logEvent.getMessage())
                .build();
    }
}
