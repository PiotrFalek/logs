package com.pfalek.logs.parsers;

import com.pfalek.logs.model.Application;
import com.pfalek.logs.model.LogEvent;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Objects.nonNull;

@Component
public class LogParserApp1 implements LogParser {

    private static final int DATE_INDEX = 0;
    private static final int TIME_INDEX = 1;
    private static final int LEVEL_INDEX = 2;
    private static final int CLAS_INDEX = 6;
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    @Override
    public List<LogEvent> parse(final String logText) {
        final String unescapeLogText = StringEscapeUtils.unescapeJson(logText);

        final List<LogEvent> logEvents = new ArrayList<>();

        final String[] lines = unescapeLogText.split("\\r\\n", -1);

        Arrays.stream(lines).forEach(line -> parse(logEvents, line));

        return logEvents;
    }

    @Override
    public Application supportedApplication() {
        return Application.APP1;
    }

    private void parse(final List<LogEvent> logEvents, final String line) {
        final String dateString = getDateTimeString(line);
        if (isParsableDate(dateString)) {
            final LogEvent logEvent = prepareLogEvent(line);
            logEvents.add(logEvent);
        } else if (logEvents.isEmpty()) {
            throw new IllegalStateException("Invalid log format");
        } else {
            updateLastLogEventMessage(logEvents, line);
        }
    }

    private void updateLastLogEventMessage(List<LogEvent> logEvents, String line) {
        final LogEvent lastLogEvent = logEvents.get(logEvents.size() - 1);
        final String message = lastLogEvent.getMessage() + "\r\n" + line;
        lastLogEvent.setMessage(message);
    }

    private LogEvent prepareLogEvent(final String line) {
        final String dateString = getDateTimeString(line);
        final LocalDateTime dateTime = parseDate(dateString);
        final String[] splitBySpace = StringUtils.split(line, " ");

        if (splitBySpace.length < CLAS_INDEX) {
            throw new IllegalStateException("Invalid log format");
        }

        final String level = splitBySpace[LEVEL_INDEX];
        final String clas = splitBySpace[CLAS_INDEX];
        final String message = StringUtils.substringAfter(StringUtils.substringAfter(line, clas), ": ");

        return LogEvent.builder()
                .dateTime(dateTime)
                .level(level)
                .clas(clas)
                .message(message)
                .application(Application.APP1)
                .build();
    }

    private String getDateTimeString(final String line) {
        final String[] split = StringUtils.split(line, " ");

        if (nonNull(split) && split.length < 2) {
            return "";
        }

        return split[DATE_INDEX] + " " + split[TIME_INDEX];
    }

    private boolean isParsableDate(final String dateTimeString) {
        try {
            parseDate(dateTimeString);
            return true;

        } catch (final DateTimeParseException ex) {
            return false;
        }
    }

    private LocalDateTime parseDate(final String dateTimeString) {
        return LocalDateTime.parse(dateTimeString, DATE_FORMAT);
    }

}
