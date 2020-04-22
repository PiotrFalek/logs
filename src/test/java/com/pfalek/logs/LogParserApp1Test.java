package com.pfalek.logs;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.pfalek.logs.model.LogEvent;
import com.pfalek.logs.parsers.LogParser;
import com.pfalek.logs.parsers.LogParserApp1;
import org.apache.commons.text.StringEscapeUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class LogParserApp1Test {

    private final ObjectMapper objectMapper = objectMapper();

    private final Path APP_LOGS_PATH = Paths.get("src", "test", "resources", "app1Logs.log");
    private final Path EXPECTED_EVENT_LOGS_PATH = Paths.get("src", "test", "resources", "expectedApp1LogEvents.json");

    private LogParser logParserApp1;

    @BeforeEach
    void setUp() {
        logParserApp1 = new LogParserApp1();
    }

    @Test
    void parsesLogs() throws IOException {
        final String textLogs = Files.readString(APP_LOGS_PATH);
        final List<LogEvent> expectedLogEvents = expectedLogEvents();

        final List<LogEvent> logEvents = logParserApp1.parse(textLogs);

//        System.out.println(StringEscapeUtils.escapeJson(textLogs));
        assertThat(logEvents).containsExactlyInAnyOrderElementsOf(expectedLogEvents);
    }

    private LogEvent createLogEvent(final LogEventForTesting logEventForTesting) {
        return LogEvent.builder()
                .dateTime(logEventForTesting.getDateTime())
                .level(logEventForTesting.getLevel())
                .clas(logEventForTesting.getClas())
                .message(logEventForTesting.getMessage())
                .application(logEventForTesting.getApplication())
                .build();
    }


    private List<LogEvent> expectedLogEvents() {
        try {
            final List<LogEventForTesting> logEventsForTesting = objectMapper.readValue(EXPECTED_EVENT_LOGS_PATH.toFile(), new TypeReference<List<LogEventForTesting>>() {
            });
            return logEventsForTesting.stream().map(this::createLogEvent).collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private ObjectMapper objectMapper() {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"));
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper;
    }

}