package com.social.server.validator;

import com.social.server.exception.GroupValidationException;
import com.social.server.http.ErrorCode;
import com.social.server.http.model.GroupModel;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GroupModelValidator {

    public static void validate(GroupModel model) {
        List<String> errors = new ArrayList<>();

        if (model == null) {
            throw new GroupValidationException("common.validation.model.incorrect", Collections.singletonList("common.validation.model.null"));
        }

        if (StringUtils.isBlank(model.getDescription())) {
            errors.add(ErrorCode.GROUP_DESCRIPTION_EMPTY);
        }
        if (StringUtils.isNotBlank(model.getDescription()) && model.getDescription().length() < 3) {
            errors.add(ErrorCode.GROUP_DESCRIPTION_INCORRECT);
        }

        if (StringUtils.isBlank(model.getName())) {
            errors.add(ErrorCode.GROUP_NAME_EMPTY);
        }
        if (StringUtils.isNotBlank(model.getName()) && model.getName().length() < 3) {
            errors.add(ErrorCode.GROUP_NAME_INCORRECT);
        }

        if (!errors.isEmpty()) {
            throw new GroupValidationException("common.validation.model.incorrect", errors);
        }
    }
}
