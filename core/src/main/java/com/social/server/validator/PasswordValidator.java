package com.social.server.validator;

import com.social.server.exception.RestorePasswordValidationException;
import com.social.server.http.ErrorCode;
import com.social.server.http.model.RestorePasswordModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PasswordValidator {

    public static void validate(RestorePasswordModel model) {
        List<String> errors = new ArrayList<>();

        if (model == null) {
            throw new RestorePasswordValidationException("common.validation.model.incorrect", Collections.singletonList("common.validation.model.null"));
        }

        if (model.getId() == 0) {
            errors.add(ErrorCode.ID_IS_EMPTY);
        }

        UserDetailValidator.validatePassword(errors, model.getPassword());

        if (!errors.isEmpty()) {
            throw new RestorePasswordValidationException("common.validation.model.incorrect", errors);
        }
    }
}
