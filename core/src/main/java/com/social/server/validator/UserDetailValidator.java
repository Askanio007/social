package com.social.server.validator;

import com.social.server.entity.Sex;
import com.social.server.exception.UserDetailsValidationException;
import com.social.server.http.ErrorCode;
import com.social.server.http.model.UserDetailsModel;
import com.social.server.validator.birthday.BirthdayValidator;
import com.social.server.validator.phone.PhoneValidator;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserDetailValidator {

    public static void validate(UserDetailsModel model) {
        List<String> errors = new ArrayList<>();

        if (model == null) {
            throw new UserDetailsValidationException("Incorrect GroupModel", Collections.singletonList("model is null"));
        }

        if (model.getId() == 0) {
            errors.add(ErrorCode.ID_IS_EMPTY);
        }

        validateName(errors, model.getName());
        validateSurname(errors, model.getSurname());
        validateSex(errors, model.getSex());

        if (StringUtils.isNotBlank(model.getCity()) && model.getCity().length() < 3) {
            errors.add(ErrorCode.DETAILS_CITY_INCORRECT);
        }

        if (StringUtils.isNotBlank(model.getCountry()) && model.getCountry().length() < 3) {
            errors.add(ErrorCode.DETAILS_COUNTRY_INCORRECT);
        }

        if (!BirthdayValidator.valid(model.getBirthday())) {
            errors.add(ErrorCode.DETAILS_BIRTHDAY_INCORRECT);
        }
        if (!PhoneValidator.valid(model.getPhone())) {
            errors.add(ErrorCode.DETAILS_PHONE_INCORRECT);
        }

        if (!errors.isEmpty()) {
            throw new UserDetailsValidationException("UserDetailsModel is incorrect", errors);
        }
    }

    static void validateName(List<String> errors, String name) {
        if (StringUtils.isBlank(name)) {
            errors.add(ErrorCode.NAME_EMPTY);
        }
        if (StringUtils.isNotBlank(name) && name.length() < 3) {
            errors.add(ErrorCode.NAME_INCORRECT);
        }
    }

    static void validateSurname(List<String> errors, String surname) {
        if (StringUtils.isBlank(surname)) {
            errors.add(ErrorCode.SURNAME_EMPTY);
        }
        if (StringUtils.isNotBlank(surname) && surname.length() < 3) {
            errors.add(ErrorCode.SURNAME_INCORRECT);
        }
    }

    static void validateSex(List<String> errors, Sex sex) {
        if(sex == null) {
            errors.add(ErrorCode.SEX_EMPTY);
        }
    }

    public static void validatePassword(List<String> errors, String password) {
        if (StringUtils.isBlank(password)) {
            errors.add(ErrorCode.PASSWORD_EMPTY);
        }
        if (StringUtils.isNotBlank(password) && password.length() < 5) {
            errors.add(ErrorCode.PASSWORD_INCORRECT);
        }
    }

}
