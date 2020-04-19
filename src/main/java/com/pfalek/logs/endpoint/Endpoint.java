package com.pfalek.logs.endpoint;

import com.pfalek.logs.model.Application;
import lombok.extern.log4j.Log4j2;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Log4j2
@RestController
public class Endpoint {


    @RequestMapping("/logs/{id}")
    public List<LogEventDto> logs(final @PathVariable("application") Application application) {
        throw new NotYetImplementedException();
    }

}
