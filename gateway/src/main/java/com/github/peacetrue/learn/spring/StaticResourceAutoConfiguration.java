package com.github.peacetrue.learn.spring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.util.Map;

/**
 * @author : xiayx
 * @since : 2020-07-04 11:27
 **/
@Slf4j
@Configuration
@EnableConfigurationProperties(StaticResourceProperties.class)
public class StaticResourceAutoConfiguration {

    private StaticResourceProperties properties;

    public StaticResourceAutoConfiguration(StaticResourceProperties properties) {
        this.properties = properties;
    }

    @Bean
    @ConditionalOnMissingBean(name = "staticResources")
    public Map<String, String> staticResources() {
        return properties.getStaticResources();
    }

    @Bean
    @ConditionalOnBean(name = "staticResources", value = Map.class)
    public RouterFunction<ServerResponse> staticResourceLocator(ResourceLoader resourceLoader,
                                                                Map<String, String> staticResources) {
        if (staticResources.isEmpty()) return null;
        RouterFunctions.Builder builder = RouterFunctions.route();
        staticResources.forEach((key, value) -> {
            log.debug("添加静态资源配置: [{}] -> [{}]", key, value);
            builder.add(RouterFunctions.resources(key, resourceLoader.getResource(value)));
        });
        return builder.build();
    }

}
