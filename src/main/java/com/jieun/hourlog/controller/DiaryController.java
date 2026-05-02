package com.jieun.hourlog.controller;

import com.jieun.hourlog.domain.User;
import com.jieun.hourlog.service.AppUserService;
import com.jieun.hourlog.service.HourLogService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Controller
public class DiaryController {

    private static final ZoneId KST = ZoneId.of("Asia/Seoul");
    private static final DateTimeFormatter DISPLAY_FORMAT =
            DateTimeFormatter.ofPattern("yyyy년 M월 d일 (E)", Locale.KOREAN);

    private final HourLogService hourLogService;
    private final AppUserService appUserService;

    public DiaryController(HourLogService hourLogService, AppUserService appUserService) {
        this.hourLogService = hourLogService;
        this.appUserService = appUserService;
    }

    @GetMapping("/")
    public String index() {
        return "redirect:/diary";
    }

    @GetMapping("/diary")
    public String diary(@RequestParam(required = false) String date,
                        @AuthenticationPrincipal UserDetails userDetails,
                        Model model) {
        LocalDate localDate = (date != null) ? LocalDate.parse(date) : LocalDate.now(KST);
        User user = appUserService.findByUsername(userDetails.getUsername());

        model.addAttribute("date", localDate);
        model.addAttribute("dateStr", localDate.toString());
        model.addAttribute("dateDisplay", localDate.format(DISPLAY_FORMAT));
        model.addAttribute("prevDate", localDate.minusDays(1).toString());
        model.addAttribute("nextDate", localDate.plusDays(1).toString());
        model.addAttribute("slots", hourLogService.getSlotsForDate(localDate, user));
        model.addAttribute("currentHour", LocalTime.now(KST).getHour());
        model.addAttribute("isToday", localDate.equals(LocalDate.now(KST)));

        return "diary";
    }
}
