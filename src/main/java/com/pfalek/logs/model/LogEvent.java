package com.pfalek.logs.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

@Data
@Entity
public class LogEvent {

    @Id
    @GeneratedValue
    private Long id;
    private LocalDate date;
    private String level;
    private String clas;
    private String message;
    private Application application;
}
