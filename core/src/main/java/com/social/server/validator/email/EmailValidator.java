package com.social.server.validator.email;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static org.apache.commons.validator.routines.EmailValidator.getInstance;

public class EmailValidator implements ConstraintValidator<EmailValid, String> {

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        return getInstance().isValid(email);
    }
}
