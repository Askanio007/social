package com.social.server.validator;

import com.social.server.exception.RegistrationValidationException;
import com.social.server.http.ErrorCode;
import com.social.server.http.model.RegistrationModel;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.social.server.validator.UserDetailValidator.*;

public class RegistrationValidator {

    public static void validate(RegistrationModel model) {
        List<String> errors = new ArrayList<>();

        if (model == null) {
            throw new RegistrationValidationException("common.validation.model.incorrect", Collections.singletonList("common.validation.model.null"));
        }

        if (StringUtils.isBlank(model.getEmail())) {
            errors.add(ErrorCode.NAME_EMPTY);
        }

        if (StringUtils.isNotBlank(model.getEmail())) {
            if (model.getEmail().length() < 5) {
                errors.add(ErrorCode.EMAIL_INCORRECT);
            }

            if (!EmailValidator.getInstance().isValid(model.getEmail())) {
                errors.add(ErrorCode.EMAIL_INCORRECT);
            }
        }

        validatePassword(errors, model.getPassword());
        validateName(errors, model.getName());
        validateSurname(errors, model.getSurname());
        validateSex(errors, model.getSex());

        if (!errors.isEmpty()) {
            throw new RegistrationValidationException("common.validation.model.incorrect",errors);
        }
    }
}
