package ru.buzynnikov.spring_security_oauth2.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Controller
@RequestMapping("/")
public class MainController {

    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    @GetMapping
    public String home(@AuthenticationPrincipal OAuth2User principal, Model model) {
        if (principal != null) {
            model.addAttribute("user", principal.getAttribute("name"));
            model.addAttribute("login", principal.getAttribute("login"));
            logger.info("Пользователь авторизирован: {}", principal.getAttribute("login").toString());
        }
        return "index";
    }

    @GetMapping("/user")
    public String userProfile(@AuthenticationPrincipal OAuth2User principal, Model model) {
        if (principal == null) {
            return "redirect:/";
        }

        String name = principal.getAttribute("name");
        String login = principal.getAttribute("login");
        System.out.println(principal.getAttributes());
        model.addAttribute("name", name);
        model.addAttribute("login", login);
        model.addAttribute("avatar", principal.getAttribute("avatar_url"));

        logger.info("Пользователь профиля авторизирован: {}", login);
        return "user/profile";
    }


    @GetMapping("/admin")
    public String adminPanel(@AuthenticationPrincipal OAuth2User principal, Model model) {
        if (principal == null) {
            return "redirect:/";
        }

        logger.info("Администратор авторизирован: {}", principal.getAttribute("login").toString());
        model.addAttribute("users", List.of("User1", "User2", "User3")); // Пример данных
        return "admin/panel";
    }


    @GetMapping("/access-denied")
    public String accessDenied(@AuthenticationPrincipal OAuth2User principal, Model model) {
        if (principal != null) {
            logger.warn("В доступе отказано пользователю: {}", principal.getAttribute("login").toString());
            model.addAttribute("username", principal.getAttribute("name"));
        }
        return "error/access-denied";
    }


//    @GetMapping("/logout")
//    public String logoutSuccess() {
//
//        return "redirect:/";
//    }
}