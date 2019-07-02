package com.social.server;

import com.social.server.configuration.WebConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@EnableTransactionManagement
@EnableConfigurationProperties(LiquibaseProperties.class)
@Import({WebConfig.class})
public class SocialApplication extends WebMvcAutoConfiguration {

    public static void main(String[] args) {
        SpringApplication.run(SocialApplication.class, args);
    }

}
