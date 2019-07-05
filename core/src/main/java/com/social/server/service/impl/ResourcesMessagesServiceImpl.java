package com.social.server.service.impl;

import com.social.server.service.ResourcesMessagesService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Properties;

@Service
public class ResourcesMessagesServiceImpl extends ReloadableResourceBundleMessageSource implements ResourcesMessagesService {

    private final static String DEFAULT_LOCALE = "ru";
    private final static String BASENAME_MESSAGE_RESOURCES = "classpath:messages";

    @Override
    public Properties getAllProperties(String locale) {
        clearCacheIncludingAncestors();
        addBasenames(BASENAME_MESSAGE_RESOURCES);
        PropertiesHolder propertiesHolder = getMergedProperties(new Locale(StringUtils.isBlank(locale) ? DEFAULT_LOCALE : locale));
        return propertiesHolder.getProperties();
    }
}
