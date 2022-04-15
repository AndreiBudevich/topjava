package ru.javawebinar.topjava.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindingResult;
import ru.javawebinar.topjava.util.ValidationUtil;

import java.util.Locale;
import java.util.stream.Collectors;

public class AbstractExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    String getUniqueEmailError() {
        return messageSource.getMessage("error.email", null, "Default",
                Locale.getDefault());
    }

    boolean checkUniqueEmailError(Exception e) {
        return ValidationUtil.getRootCause(e).getMessage().contains("users_unique_email_idx");
    }

    public static String getErrors(BindingResult result) {
        return result.getFieldErrors().stream()
                .map(fe -> String.format("[%s] %s", fe.getField(), fe.getDefaultMessage()))
                .collect(Collectors.joining("<br>"));
    }
}
