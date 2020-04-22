package com.pfalek.logs.endpoint;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "logEvents")
public class LogEventsOutput {
    private List<LogEventOutput> logEvent;
}
