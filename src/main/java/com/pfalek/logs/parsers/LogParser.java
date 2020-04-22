package com.pfalek.logs.parsers;

import com.pfalek.logs.model.Application;
import com.pfalek.logs.model.LogEvent;

import java.util.List;

public interface LogParser {
    List<LogEvent> parse(final String text);

    Application supportedApplication();
}
