package com.social.server.validator.phone;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Slf4j
public class PhoneValidator implements ConstraintValidator<PhoneValid, String> {

    private static final PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();

    @Override
    public boolean isValid(String phone, ConstraintValidatorContext constraintValidatorContext) {
        return valid(phone);
    }

    public static boolean valid(String phone) {
        try {
            return phoneUtil.isValidNumber(phoneUtil.parse(phone, null));
        } catch (NumberParseException e) {
            log.debug("Parse number is failed. Phone={}", phone);
            return false;
        }
    }
}
