package com.github.peacetrue.learn.spring;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author : xiayx
 * @since : 2020-07-04 12:18
 **/
@SpringBootTest
class ConfigApplicationTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void applicationContextIsOK() {
        assertNotNull(applicationContext);
    }

}
