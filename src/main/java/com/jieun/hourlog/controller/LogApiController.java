package com.jieun.hourlog.controller;

import com.jieun.hourlog.domain.User;
import com.jieun.hourlog.domain.HourLog;
import com.jieun.hourlog.dto.LogRequest;
import com.jieun.hourlog.service.AppUserService;
import com.jieun.hourlog.service.HourLogService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api")
public class LogApiController {

    private final HourLogService hourLogService;
    private final AppUserService appUserService;

    public LogApiController(HourLogService hourLogService, AppUserService appUserService) {
        this.hourLogService = hourLogService;
        this.appUserService = appUserService;
    }

    @PostMapping("/log")
    public ResponseEntity<HourLog> saveLog(@RequestBody LogRequest request,
                                           @AuthenticationPrincipal UserDetails userDetails) {
        User user = appUserService.findByUsername(userDetails.getUsername());
        HourLog log = hourLogService.save(
                LocalDate.parse(request.date()), request.hour(), request.content(), user);
        return ResponseEntity.ok(log);
    }

    @DeleteMapping("/log/{id}")
    public ResponseEntity<Void> deleteLog(@PathVariable Long id,
                                          @AuthenticationPrincipal UserDetails userDetails) {
        User user = appUserService.findByUsername(userDetails.getUsername());
        hourLogService.delete(id, user);
        return ResponseEntity.noContent().build();
    }
}
