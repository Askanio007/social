package com.social.server.util;

import com.social.server.http.model.GroupModel;
import org.junit.Assert;
import org.junit.Test;

public class JsonUtilTest {

    private String EXPECTED_JSON_STRING = "{\"id\":2,\"name\":\"test\",\"description\":\"test\"}";

    @Test
    public void checkConvertToJson() {
        GroupModel model = new GroupModel();
        model.setId(2);
        model.setName("test");
        model.setDescription("test");
        String ee = JsonUtil.toJson(model);
        Assert.assertEquals(EXPECTED_JSON_STRING, ee);
    }
}
