package com.social.server.validator;

import com.social.server.exception.PublicMessageValidationException;
import com.social.server.http.ErrorCode;
import com.social.server.http.model.PublicMessageModel;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PublicMessageValidator {

    public static void validate(PublicMessageModel model) {
        List<String> errors = new ArrayList<>();

        if (model == null) {
            throw new PublicMessageValidationException("Incorrect PublicMessageModel", Collections.singletonList("model is null"));
        }

        if (model.getSenderId() == 0 || model.getRecipientId() == 0) {
            errors.add(ErrorCode.ID_IS_EMPTY);
        }

        if (StringUtils.isBlank(model.getMessage())) {
            errors.add(ErrorCode.MESSAGE_IS_EMPTY);
        }

        if (model.getRecipientType() == null) {
            errors.add(ErrorCode.RECIPIENT_TYPE_IS_EMPTY);
        }

        if (!errors.isEmpty()) {
            throw new PublicMessageValidationException("PublicMessageModel is incorrect", errors);
        }

    }
}
