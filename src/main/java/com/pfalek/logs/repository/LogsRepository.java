package com.pfalek.logs.repository;

import com.pfalek.logs.model.Application;
import com.pfalek.logs.model.LogEvent;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface
LogsRepository extends JpaRepository<LogEvent, Long> {
    @Query(value = "SELECT l FROM LogEvents l WHERE l.dateTime >= :startDateTime AND l.dateTime <= :endDateTime AND l.application = :application")
    List<LogEvent> findAllByDateAndApplication(@Param("startDateTime") final LocalDateTime startDateTime,
                                               @Param("endDateTime") final LocalDateTime endDateTime,
                                               @Param("application") final Application application,
                                               final Pageable pageable);

}

