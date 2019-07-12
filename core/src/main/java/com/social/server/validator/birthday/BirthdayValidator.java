package com.social.server.validator.birthday;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;

public class BirthdayValidator implements ConstraintValidator<BirthdayValid, LocalDateTime> {

    @Override
    public boolean isValid(LocalDateTime s, ConstraintValidatorContext constraintValidatorContext) {
        return valid(s);
    }

    public static boolean valid(LocalDateTime s) {
        if (s == null) {
            return false;
        }

        return s.isBefore(LocalDateTime.now());
    }
}
