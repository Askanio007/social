package com.social.server.util;

import com.social.server.dto.UserDto;
import org.junit.Assert;
import org.junit.Test;

public class TokenUtilTest {

    @Test
    public void checkGenerateToken() {
        UserDto user = new UserDto();
        user.setEmail("test");
        user.setId(0L);
        String token = TokenUtil.generateToken(user);
        UserDto newUser  = TokenUtil.parseToken(token);
        Assert.assertEquals(user.getEmail(), newUser.getEmail());
        Assert.assertEquals(user.getId(), newUser.getId());
    }
}
