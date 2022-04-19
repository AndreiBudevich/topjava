package ru.javawebinar.topjava.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import ru.javawebinar.topjava.util.ValidationUtil;

import java.util.Map;

public class ExceptionMessage {

    private static final Map<String, String> CONSTRAINS_I18N_MAP = Map.of(
            "users_unique_email_idx", "error.user.email",
            "meals_unique_user_datetime_idx", "error.datetime");

    @Autowired
    private MessageSource messageSource;

    public String getErrorLocalMessage(Exception e) {
        String lowerCaseMsg = ValidationUtil.getRootCause(e).getMessage().toLowerCase();
        String message = "";
        for (Map.Entry<String, String> entry : CONSTRAINS_I18N_MAP.entrySet()) {
            if (lowerCaseMsg.contains(entry.getKey())) {
                message = entry.getValue();
            }
        }
        return messageSource.getMessage(message, null, "Default",
                LocaleContextHolder.getLocale());
    }
}
