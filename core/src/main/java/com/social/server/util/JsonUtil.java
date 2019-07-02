package com.social.server.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonUtil {

    private static ObjectMapper mapper = new ObjectMapper();

    public static String toJson(Object ob) {
        try {
            return mapper.writeValueAsString(ob);
        } catch (JsonProcessingException e) {
            log.error("Convert object to json is failed: {}", ob , e);
            return null;

        }
    }
}
