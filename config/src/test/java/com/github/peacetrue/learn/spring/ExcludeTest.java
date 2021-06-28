package com.github.peacetrue.learn.spring;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;

/**
 * @author : xiayx
 * @since : 2020-07-23 16:24
 **/
@SpringBootTest
//@ActiveProfiles("b")
public class ExcludeTest {

    @Profile("!a")
    @Configuration
    public static class C {
        @Bean
        public String name() {
            return "C";
        }
    }

    @Profile("a")
    @Configuration
    public static class A {
        @Bean
        public String name() {
            return "A";
        }
    }

    @Autowired
    public String name;

    @Test
    void name() {
        System.out.println(name);
        Assertions.assertEquals("C", name);
    }
}
