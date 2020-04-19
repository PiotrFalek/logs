package com.pfalek.logs.endpoint;

import com.pfalek.logs.model.Application;
import com.pfalek.logs.model.LogEvent;
import com.pfalek.logs.repository.LogsRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Log4j2
@RestController
public class Endpoint {

    private final LogsRepository logsRepository;

    @GetMapping("/logs/{application}")
    public List<LogEventDto> findLogs(@PathVariable("application") final Application application,
                                      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) final LocalDate date,
                                      @RequestParam final int page, @RequestParam final int pageSize) {
        final List<LogEvent> logEvents = logsRepository.findAllByDateAndApplication(date, application, PageRequest.of(page, pageSize));
        return logEvents.stream()
                .map(LogEventDto::from)
                .collect(Collectors.toList());
    }

    @PostMapping("/logs")
    public void saveLog(@RequestBody final LogEvent logEvent) {
        logsRepository.save(logEvent);
    }

}
