package com.pfalek.logs.parsers;

import com.pfalek.logs.model.Application;
import com.pfalek.logs.model.LogEvent;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class ProxyLogParser {
    private final List<LogParser> logParsers;

    public List<LogEvent> parse(final String text, final Application application) {
        final LogParser logParser = provideParser(application);
        return logParser.parse(text);
    }

    private LogParser provideParser(final Application application) {
        final List<LogParser> logParsers = this.logParsers.stream()
                .filter(parser -> parser.supportedApplication().equals(application))
                .collect(Collectors.toList());

        if (logParsers.size() == 1) {
            return logParsers.get(0);
        }

        throw new IllegalStateException("Wrong log parsers configuration");
    }
}
