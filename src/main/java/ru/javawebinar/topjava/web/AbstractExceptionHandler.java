package ru.javawebinar.topjava.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindingResult;
import ru.javawebinar.topjava.util.ValidationUtil;

import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

public class AbstractExceptionHandler {

    private static final Map<String, String> CONSTRAINS_I18N_MAP = Map.of(
            "users_unique_email_idx", "error.email",
            "meals_unique_user_datetime_idx", "error.datetime");

    @Autowired
    private MessageSource messageSource;

    String getErrorOnUniqueLocal(String errorOnUnique) {
        return messageSource.getMessage(errorOnUnique, null, "Default",
                Locale.getDefault());
    }

    String getErrorOnUnique(Exception e) {
        String lowerCaseMsg = ValidationUtil.getRootCause(e).getMessage().toLowerCase();
        for (Map.Entry<String, String> entry : CONSTRAINS_I18N_MAP.entrySet()) {
            if (lowerCaseMsg.contains(entry.getKey())) {
                return entry.getValue();
            }
        }
        return "";
    }

    public static String getErrors(BindingResult result) {
        return result.getFieldErrors().stream()
                .map(fe -> String.format("[%s] %s", fe.getField(), fe.getDefaultMessage()))
                .collect(Collectors.joining("<br>"));
    }
}
