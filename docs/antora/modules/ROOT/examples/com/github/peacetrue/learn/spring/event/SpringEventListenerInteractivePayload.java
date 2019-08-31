package com.github.peacetrue.learn.spring.event;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.PayloadApplicationEvent;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 【spring 事件交互式负载】
 * <p>
 * 虽然从理论上讲，事件的多个监听器之间应该是独立，相互关联的事情应该在同一个监听器中完成；
 * 但在开发过程中为了方便和快捷，需要在多个监听器之间共享一些变量以及异常。
 *
 * @author xiayx
 */
public class SpringEventListenerInteractivePayload {


    /** 可交互的负载 */
    public static class InteractivePayload<S> {

        /** 原始负载 */
        private S payload;
        /** 共享变量 */
        private Map<String, Object> variables = new LinkedHashMap<>();
        /** 共享异常 */
        private List<Throwable> exceptions = new LinkedList<>();

        public InteractivePayload(S payload) {
            this.payload = payload;
        }

        public S getPayload() {
            return payload;
        }

        @SuppressWarnings("unchecked")
        public <T> T setVariable(String name, Object value) {
            return (T) variables.put(name, value);
        }

        @SuppressWarnings("unchecked")
        public <T> T getVariable(String name) {
            return (T) variables.get(name);
        }

        public void addException(Throwable throwable) {
            this.exceptions.add(throwable);
        }

        public List<Throwable> getExceptions() {
            return Collections.unmodifiableList(this.exceptions);
        }
    }

    /** 小玉 */
    @Component
    public static class XiaoYu {

        @Autowired
        private ApplicationEventPublisher eventPublisher;

        /** 早上起床了 */
        public void wakeUp() {
            System.out.println("小玉：起床了");
            eventPublisher.publishEvent(new PayloadApplicationEvent<>("wakeUp", new InteractivePayload<>(this)));
        }

        /** 晚上睡觉了 */
        public void sleep() {
            System.out.println("小玉：睡觉了");
            eventPublisher.publishEvent(new PayloadApplicationEvent<>("sleep", new InteractivePayload<>(this)));
        }
    }


    /** 小明：最先执行 */
    @Component
    public static class XiaoMingFirst {

        /**
         * 问题：
         * 参数使用{@code PayloadApplicationEvent<InteractivePayload<?>>}，
         * 而非{@code PayloadApplicationEvent<InteractivePayload<XiaoYu>>}，
         * 因为后者 spring 不支持。
         * 很奇怪，前者在 Intellij IDEA 中不被支持，而后者在 Intellij IDEA 中被支持。
         * Intellij IDEA 支持时会在左边行号附近显示小标记，点击后可以在触发代码和监听代码之间相互跳转。
         */
        @EventListener(condition = "#event.source=='wakeUp'")
        @Order(Ordered.HIGHEST_PRECEDENCE)
        public void xiaoYuWakeUp(PayloadApplicationEvent<InteractivePayload<?>> event) {
            event.getPayload().setVariable("早餐", "一杯咖啡一个汉堡");
            event.getPayload().addException(new IllegalStateException("起晚了"));
        }

        @EventListener(condition = "#event.source=='sleep'")
        @Order(Ordered.HIGHEST_PRECEDENCE)
        public void xiaoYuSleep(PayloadApplicationEvent<InteractivePayload<?>> event) {
            event.getPayload().setVariable("爱情故事", "牛郎和七仙女");
            event.getPayload().addException(new IllegalStateException("睡早了"));
        }
    }

    /** 小明：最后执行 */
    @Component
    public static class XiaoMingLast {

        @EventListener(condition = "#event.source=='wakeUp'")
        @Order(Ordered.LOWEST_PRECEDENCE)
        public void xiaoYuWakeUp(PayloadApplicationEvent<InteractivePayload<?>> event) {
            if (event.getPayload().getExceptions().stream().anyMatch(exception -> exception.getMessage().equals("起晚了"))) {
                System.out.println(String.format("小明：叫了外卖[%s]", (String) event.getPayload().getVariable("早餐")));
            } else {
                System.out.println(String.format("小明：准备了早餐[%s]", (String) event.getPayload().getVariable("早餐")));
            }
        }

        @EventListener(condition = "#event.source=='sleep'")
        @Order(Ordered.LOWEST_PRECEDENCE)
        public void xiaoYuSleep(PayloadApplicationEvent<InteractivePayload<?>> event) {
            if (event.getPayload().getExceptions().stream().anyMatch(exception -> exception.getMessage().equals("睡早了"))) {
                System.out.println(String.format("小明：播放录音[%s]", (String) event.getPayload().getVariable("爱情故事")));
            } else {
                System.out.println(String.format("小明：讲爱情故事[%s]", (String) event.getPayload().getVariable("爱情故事")));
            }
        }

    }

    public static void main(String[] args) {
        BeanFactory beanFactory = new AnnotationConfigApplicationContext(
                XiaoYu.class,
                XiaoMingFirst.class,
                XiaoMingLast.class
        );
        XiaoYu xiaoYu = beanFactory.getBean(XiaoYu.class);
        xiaoYu.wakeUp();
        xiaoYu.sleep();
    }


}
