package ru.buzynnikov.spring_security_oauth2.controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

@Controller
public class CustomErrorController implements ErrorController {

    private static final Logger logger = LoggerFactory.getLogger(CustomErrorController.class);

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        // Получаем статус ошибки
        Integer statusCode = (Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Exception exception = (Exception) request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);

        // Логируем ошибку
        if (exception != null) {
            logger.error("Ошибка: ", exception);
        } else if (statusCode != null) {
            logger.error("Ошибка с кодом: {}", statusCode);
        }

        // Добавляем информацию в модель
        model.addAttribute("statusCode", statusCode != null ? statusCode : 500);
        model.addAttribute("errorMessage", getErrorMessage(statusCode));
        model.addAttribute("timestamp", new Date());

        return "error/error";
    }

    private String getErrorMessage(Integer statusCode) {
        if (statusCode == null) {
            return "Произошла неизвестная ошибка";
        }

        return switch (statusCode) {
            case 400 -> "Некорректный запрос";
            case 401 -> "Требуется авторизация";
            case 403 -> "Доступ запрещён";
            case 404 -> "Страница не найдена";
            case 500 -> "Ошибка сервера";
            default -> "Ошибка: " + statusCode;
        };
    }

}