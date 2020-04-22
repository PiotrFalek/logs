package com.pfalek.logs.endpoint;

import com.pfalek.logs.model.Application;
import com.pfalek.logs.model.LogEvent;
import com.pfalek.logs.parsers.ProxyLogParser;
import com.pfalek.logs.repository.LogsRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@Transactional
public class Endpoint {

    private final LogsRepository logsRepository;
    private final ProxyLogParser proxyLogParser;

    @GetMapping("/logs/{application}")
    public List<LogEventOutput> findLogs(@PathVariable("application") final Application application,
                                         @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) final LocalDate date,
                                         @RequestParam final int page, @RequestParam final int pageSize) {
        final List<LogEvent> logEvents = logsRepository.findAllByDateAndApplication(LocalDateTime.of(date, LocalTime.MIN),
                LocalDateTime.of(date, LocalTime.MAX), application, PageRequest.of(page, pageSize));
        return logEvents.stream()
                .map(LogEventOutput::from)
                .collect(Collectors.toList());
    }

    @PostMapping("/logs")
    public void saveLogs(@RequestBody final LogInput logInput) {
        final List<LogEvent> logEvents = proxyLogParser.parse(logInput.getLogText(), logInput.getApplication());
        logEvents.forEach(LogEvent::validate);
        logsRepository.saveAll(logEvents);
    }

}
