package com.github.peacetrue.learn.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @author : xiayx
 * @since : 2020-07-04 11:01
 **/
@SpringBootApplication

public class ConfigApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConfigApplication.class, args);
    }

    @Profile("list")
    @Configuration
    @EnableConfigurationProperties(ConfigListProperties.class)
    public static class ConfigListConfiguration {

    }

    @Profile("map")
    @Configuration
    @EnableConfigurationProperties(ConfigMapProperties.class)
    public static class ConfigMapConfiguration {

    }

    @Profile("equivalent-map")
    @Configuration
    @EnableConfigurationProperties(ConfigEquivalentMapProperties.class)
    public static class ConfigEquivalentMapConfiguration {

    }
}
