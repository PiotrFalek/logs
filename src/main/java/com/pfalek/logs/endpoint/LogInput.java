package com.pfalek.logs.endpoint;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pfalek.logs.model.Application;
import lombok.Value;

@Value
public class LogInput {
    private final String logText;
    private final Application application;

    @JsonCreator
    public LogInput(@JsonProperty("logText") final String logText, @JsonProperty("application") final Application application) {
        this.logText = logText;
        this.application = application;
    }

}
