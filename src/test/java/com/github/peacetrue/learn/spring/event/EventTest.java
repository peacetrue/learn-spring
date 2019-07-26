package com.github.peacetrue.learn.spring.event;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author xiayx
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EventTest.class,
        properties = {
                "logging.level.root=off",
                "logging.level.com.github.peacetrue=debug",
        })
@Configuration
public class EventTest {

    @Component
    public static class Trigger {

        private Logger logger = LoggerFactory.getLogger(getClass());
        @Autowired
        private ApplicationEventPublisher eventPublisher;

        public void trigger(String content) {
            logger.info("before trigger [{}]", content);
            eventPublisher.publishEvent(content);
            logger.info("after trigger [{}]", content);
        }
    }

    @Component
    public static class Handler {
        private Logger logger = LoggerFactory.getLogger(getClass());

        @EventListener
        @Order(Ordered.HIGHEST_PRECEDENCE)
        public void handle(String content) {
            logger.info("content: {} ", content);
        }
    }

    @Component
    public static class Handler2 {
        private Logger logger = LoggerFactory.getLogger(getClass());

        @EventListener
        @Order(Ordered.LOWEST_PRECEDENCE)
        public void handle(String content) {
            logger.info("content: {} ", content);
            throw new IllegalStateException();
        }
    }


    @Autowired
    private Trigger trigger;

    @Test
    public void name() {
        trigger.trigger("someone");
    }
}
