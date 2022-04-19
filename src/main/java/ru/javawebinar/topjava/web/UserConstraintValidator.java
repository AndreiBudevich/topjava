package ru.javawebinar.topjava.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.to.UserTo;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UserConstraintValidator implements ConstraintValidator<UniqueEmail, UserTo> {

    @Autowired
    UserService userService;

    @Autowired
    private MessageSource messageSource;

    @Override
    public void initialize(UniqueEmail uniqueEmail) {

    }

    @Override
    public boolean isValid(UserTo userTo, ConstraintValidatorContext context) {
        try {
            userService.getByEmail(userTo.getEmail());
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                            messageSource.getMessage("error.user.email", null, "Default",
                                    LocaleContextHolder.getLocale()))
                    .addPropertyNode("email").addConstraintViolation();
            return false;
        } catch (NotFoundException e) {
            return true;
        }
    }
}



