package com.pfalek.logs.parsers;

import com.pfalek.logs.model.Application;
import com.pfalek.logs.model.LogEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ProxyLogParserTest {

    @Mock
    private LogParser app1LogParser;

    @Mock
    private LogParser app2LogParser;

    @Test
    void parsesLogs() {
        final ProxyLogParser proxyLogParser = new ProxyLogParser(Arrays.asList(app1LogParser, app2LogParser));
        final LogEvent logEvent = LogEvent.builder().message("dummy message").build();

        when(app1LogParser.supportedApplication()).thenReturn(Application.APP1);
        when(app1LogParser.parse(any())).thenReturn(Collections.singletonList(logEvent));
        when(app2LogParser.supportedApplication()).thenReturn(Application.APP2);

        List<LogEvent> logEvents = proxyLogParser.parse("", Application.APP1);

        assertThat(logEvents).containsExactlyInAnyOrder(logEvent);
    }

    @Test
    void throwsExceptionWhenThereParserForAppIsNotDefined() {
        final ProxyLogParser proxyLogParser = new ProxyLogParser(Collections.singletonList(app1LogParser));
        when(app1LogParser.supportedApplication()).thenReturn(Application.APP2);

        assertThrows(IllegalStateException.class, () -> {
            proxyLogParser.parse("", Application.APP1);
        });

    }

    @Test
    void throwsExceptionWhenThereAreNoUniqueLogParsers() {
        final ProxyLogParser proxyLogParser = new ProxyLogParser(Arrays.asList(app1LogParser, app2LogParser));

        when(app1LogParser.supportedApplication()).thenReturn(Application.APP1);
        when(app2LogParser.supportedApplication()).thenReturn(Application.APP1);

        assertThrows(IllegalStateException.class, () -> {
            proxyLogParser.parse("", Application.APP1);
        });

    }

}