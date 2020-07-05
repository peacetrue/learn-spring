package com.github.peacetrue.learn.spring;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.LinkedList;
import java.util.List;

/**
 * @author : xiayx
 * @since : 2020-07-02 23:04
 **/
@Data
@ConfigurationProperties("peacetrue.learn")
public class ConfigListProperties {

    @Data
    public static class Resource {
        private String pattern;
        private String location;
    }

    private List<Resource> staticResources = new LinkedList<>();

}
