package com.jieun.hourlog.controller;

import com.jieun.hourlog.service.AppUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegisterController {

    private final AppUserService appUserService;

    public RegisterController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @GetMapping("/register")
    public String registerForm() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String username,
                           @RequestParam String password,
                           @RequestParam String passwordConfirm,
                           Model model) {
        if (username.isBlank() || username.length() < 3) {
            model.addAttribute("error", "아이디는 3자 이상이어야 합니다.");
            return "register";
        }
        if (password.length() < 6) {
            model.addAttribute("error", "비밀번호는 6자 이상이어야 합니다.");
            return "register";
        }
        if (!password.equals(passwordConfirm)) {
            model.addAttribute("error", "비밀번호가 일치하지 않습니다.");
            return "register";
        }
        if (appUserService.existsByUsername(username)) {
            model.addAttribute("error", "이미 사용 중인 아이디입니다.");
            return "register";
        }

        appUserService.register(username, password);
        return "redirect:/login?registered";
    }
}
