package com.social.server.validator;

import com.social.server.validator.birthday.BirthdayValidator;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;

public class BirthdayValidatorTest {

    private BirthdayValidator validator = new BirthdayValidator();

    @Test
    public void failValidationBirthdayNull() {
        Assert.assertFalse(validator.isValid(null, null));
    }

    @Test
    public void failValidationBirthdayAfterToday() {
        Assert.assertFalse(validator.isValid(LocalDateTime.now().plusDays(1), null));
    }

    @Test
    public void successValidation() {
        Assert.assertTrue(validator.isValid(LocalDateTime.now().minusDays(10), null));
    }
}
