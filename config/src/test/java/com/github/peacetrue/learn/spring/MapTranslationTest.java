package com.github.peacetrue.learn.spring;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Map;

/**
 * @author : xiayx
 * @since : 2020-07-04 13:58
 **/
@ActiveProfiles({"map", "translation"})
@SpringBootTest
public class MapTranslationTest {

    @Autowired
    private ConfigMapProperties properties;

    @Test
    void sizeToBe4() {
        Map<String, String> resources = properties.getStaticResources();
        Assertions.assertEquals(4, resources.size());
        Assertions.assertTrue(resources.containsKey("/test/**"));
    }
}
