package ru.javawebinar.topjava.web;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UserConstraintValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueEmail {
    String message() default "error.user.email";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}


