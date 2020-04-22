package com.pfalek.logs.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@AllArgsConstructor
@Builder
@Data
@Entity(name = "LogEvents")
@NoArgsConstructor
public class LogEvent {

    @Id
    @GeneratedValue
    private Long id;

    private LocalDateTime dateTime;

    @Column(length = 10)
    private String level;

    @Column(length = 250)
    private String clas;

    @Column(length = 20000)
    private String message;

    private Application application;

    public void validate() {
        validate(level, "level", 10);
        validate(clas, "clas", 250);
        validate(message, "message", 20000);
    }

    private void validate(final String field, final String fieldName, final int maxLength) {
        final int length = StringUtils.length(field);
        if (length > maxLength) {
            final String description = "Field %s should not be longer then %s chars. Field length: %s Field value: %s";
            throw new IllegalStateException(String.format(description, fieldName, maxLength, length, field));
        }
    }

}
