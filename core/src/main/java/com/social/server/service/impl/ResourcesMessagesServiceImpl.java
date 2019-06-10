package com.social.server.service.impl;

import com.social.server.service.ResourcesMessagesService;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Properties;

@Service
public class ResourcesMessagesServiceImpl extends ReloadableResourceBundleMessageSource implements ResourcesMessagesService {

    @Override
    public Properties getAllProperties(String locale) {
        clearCacheIncludingAncestors();
        addBasenames("classpath:messages");
        PropertiesHolder propertiesHolder = getMergedProperties(new Locale(locale));
        return propertiesHolder.getProperties();
    }
}
