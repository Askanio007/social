package com.social.server.validator;

import com.social.server.validator.phone.PhoneValidator;
import org.junit.Assert;
import org.junit.Test;

public class PhoneValidatorTest {

    private PhoneValidator phoneValidator = new PhoneValidator();

    @Test
    public void failValidatePhoneIsNull() {
        Assert.assertFalse(phoneValidator.isValid(null, null));
    }

    @Test
    public void failValidatePhoneIsEmpty() {
        Assert.assertFalse(phoneValidator.isValid("", null));
    }

    @Test
    public void failValidatePhoneIsNotNumber() {
        Assert.assertFalse(phoneValidator.isValid("asdssad", null));
    }

    @Test
    public void failValidatePhoneIsNotCorrectNumber() {
        Assert.assertFalse(phoneValidator.isValid("3123", null));
    }

    @Test
    public void successValidate() {
        Assert.assertTrue(phoneValidator.isValid("+79536526598", null));
    }

}
