package com.social.server.util;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;

public class DateFormatterUtilTest {

    @Test
    public void checkDateWithoutTime() {
        LocalDateTime date = LocalDateTime.of(2019, 5, 12, 18, 0, 0);
        Assert.assertEquals(DateFormatterUtil.withoutTimeFormat(date), "12.05.2019");
    }

    @Test
    public void checkDateWithTime() {
        LocalDateTime date = LocalDateTime.of(2019, 5, 12, 18, 33, 0);
        Assert.assertEquals(DateFormatterUtil.withTimeFormat(date), "12.05.2019 18:33");
    }

    @Test
    public void checkDateInputFormat() {
        LocalDateTime date = LocalDateTime.of(2019, 5, 12, 18, 0, 0);
        Assert.assertEquals(DateFormatterUtil.inputFormat(date), "2019-05-12");
    }
}
