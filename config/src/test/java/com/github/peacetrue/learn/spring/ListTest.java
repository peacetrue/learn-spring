package com.github.peacetrue.learn.spring;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

/**
 * @author : xiayx
 * @since : 2020-07-04 13:58
 **/
@ActiveProfiles("list")
@SpringBootTest
public class ListTest {

    @Autowired
    private ConfigListProperties properties;

    @Test
    void sizeToBe2() {
        List<ConfigListProperties.Resource> resources = properties.getStaticResources();
        Assertions.assertEquals(2, resources.size());
        Assertions.assertEquals("/test/**", resources.get(0).getPattern());
    }
}
