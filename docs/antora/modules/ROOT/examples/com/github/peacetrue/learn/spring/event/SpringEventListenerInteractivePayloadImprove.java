package com.github.peacetrue.learn.spring.event;

import com.github.peacetrue.learn.spring.event.SpringEventListenerInteractivePayload.InteractivePayload;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.PayloadApplicationEvent;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 【spring 事件交互式负载改进】
 * <p>
 * 介于{@link SpringEventListenerInteractivePayload.XiaoMingFirst}中存在的参数问题，
 * 所以提供了一个自定义的负载事件{@link PayloadEvent}
 *
 * @author xiayx
 */
public class SpringEventListenerInteractivePayloadImprove {


    /** 自定义的负载事件用于取代{@link PayloadApplicationEvent} */
    public static class PayloadEvent<T> extends ApplicationEvent {

        private T payload;

        public PayloadEvent(String eventName, T payload) {
            super(eventName);
            this.payload = payload;
        }

        public String getEventName() {
            return (String) getSource();
        }

        public T getPayload() {
            return payload;
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
            eventPublisher.publishEvent(new PayloadEvent<>("wakeUp", new InteractivePayload<>(this)));
        }

        /** 晚上睡觉了 */
        public void sleep() {
            System.out.println("小玉：睡觉了");
            eventPublisher.publishEvent(new PayloadEvent<>("sleep", new InteractivePayload<>(this)));
        }
    }


    /** 小明：最先执行 */
    @Component
    public static class XiaoMingFirst {

        /**
         * 注意：
         * 参数使用{@code PayloadApplicationEvent<InteractivePayload<?>>}，而非{@code PayloadApplicationEvent<InteractivePayload<XiaoYu>>}，
         * 因为后者 spring 不支持。
         * 很奇怪，前者在 Intellij IDEA 中不被支持，而后者在 Intellij IDEA 中被支持。
         * Intellij IDEA 支持时会在左边行号附近显示小标记，点击后可以在触发代码和监听代码之间相互跳转。
         */
        @EventListener(condition = "#event.source=='wakeUp'")
        @Order(Ordered.HIGHEST_PRECEDENCE)
        public void xiaoYuWakeUp(PayloadEvent<InteractivePayload<XiaoYu>> event) {
            event.getPayload().setVariable("早餐", "一杯咖啡一个汉堡");
            event.getPayload().addException(new IllegalStateException("起晚了"));
        }

        @EventListener(condition = "#event.source=='sleep'")
        @Order(Ordered.HIGHEST_PRECEDENCE)
        public void xiaoYuSleep(PayloadEvent<InteractivePayload<XiaoYu>> event) {
            event.getPayload().setVariable("爱情故事", "牛郎和七仙女");
            event.getPayload().addException(new IllegalStateException("睡早了"));
        }
    }

    /** 小明：最后执行 */
    @Component
    public static class XiaoMingLast {

        @EventListener(condition = "#event.source=='wakeUp'")
        @Order(Ordered.LOWEST_PRECEDENCE)
        public void xiaoYuWakeUp(PayloadEvent<InteractivePayload<XiaoYu>> event) {
            if (event.getPayload().getExceptions().stream().anyMatch(exception -> exception.getMessage().equals("起晚了"))) {
                System.out.println(String.format("小明：叫了外卖[%s]", (String) event.getPayload().getVariable("早餐")));
            } else {
                System.out.println(String.format("小明：准备了早餐[%s]", (String) event.getPayload().getVariable("早餐")));
            }
        }

        @EventListener(condition = "#event.source=='sleep'")
        @Order(Ordered.LOWEST_PRECEDENCE)
        public void xiaoYuSleep(PayloadEvent<InteractivePayload<XiaoYu>> event) {
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
