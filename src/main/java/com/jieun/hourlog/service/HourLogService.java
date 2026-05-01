package com.jieun.hourlog.service;

import com.jieun.hourlog.domain.HourLog;
import com.jieun.hourlog.domain.User;
import com.jieun.hourlog.dto.HourSlot;
import com.jieun.hourlog.repository.HourLogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class HourLogService {

    private final HourLogRepository repository;

    public HourLogService(HourLogRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<HourSlot> getSlotsForDate(LocalDate date, User user) {
        Map<Integer, HourLog> logMap = repository.findByLogDateAndUser(date, user)
                .stream()
                .collect(Collectors.toMap(HourLog::getHour, log -> log));

        List<HourSlot> slots = new ArrayList<>(24);
        for (int h = 0; h < 24; h++) {
            HourLog log = logMap.get(h);
            if (log != null) {
                slots.add(new HourSlot(h, log.getId(), log.getContent()));
            } else {
                slots.add(new HourSlot(h, null, null));
            }
        }
        return slots;
    }

    @Transactional
    public HourLog save(LocalDate date, int hour, String content, User user) {
        HourLog log = repository.findByLogDateAndHourAndUser(date, hour, user)
                .orElse(new HourLog());
        log.setUser(user);
        log.setLogDate(date);
        log.setHour(hour);
        log.setContent(content);
        return repository.save(log);
    }

    @Transactional
    public void delete(Long id, User user) {
        repository.findById(id).ifPresent(log -> {
            if (log.getUser().getId().equals(user.getId())) {
                repository.delete(log);
            }
        });
    }
}
