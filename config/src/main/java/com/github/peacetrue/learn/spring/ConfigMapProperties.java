package com.github.peacetrue.learn.spring;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author : xiayx
 * @since : 2020-07-02 23:04
 **/
@Data
@ConfigurationProperties("peacetrue.learn")
public class ConfigMapProperties {

    private Map<String, String> staticResources = new LinkedHashMap<>();

}
