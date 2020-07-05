package com.github.peacetrue.learn.spring;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author : xiayx
 * @since : 2020-07-04 11:04
 **/
@SpringBootTest
class GatewayApplicationTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void applicationContextIsOK() {
        assertNotNull(applicationContext);
    }
}
