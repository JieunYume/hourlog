package com.jieun.hourlog.repository;

import com.jieun.hourlog.domain.HourLog;
import com.jieun.hourlog.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface HourLogRepository extends JpaRepository<HourLog, Long> {

    List<HourLog> findByLogDateAndUser(LocalDate logDate, User user);

    Optional<HourLog> findByLogDateAndHourAndUser(LocalDate logDate, int hour, User user);
}
