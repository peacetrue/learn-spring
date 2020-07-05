package com.github.peacetrue.learn.spring.event;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.PayloadApplicationEvent;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 *【spring 事件监听器执行顺序】
 * <p>
 * 在小玉起床后和睡觉前，小明如果想做多项事情，如何控制这些事情的执行顺序呢？
 * 在{@link EventListener}监听的方法上，通过{@link Order}控制。
 *
 * @author xiayx
 */
public class SpringEventListenerOrder {

    /** 小明：最先执行 */
    @Component
    public static class XiaoMingFirst {

        @EventListener(condition = "#event.source=='wakeUp'")
        @Order(Ordered.HIGHEST_PRECEDENCE)
        public void xiaoYuWakeUp(PayloadApplicationEvent<SpringEventModelOnSpringEvent.XiaoYu> event) {
            System.out.println("小明：赶紧起床");
        }

        @EventListener(condition = "#event.source=='sleep'")
        @Order(Ordered.HIGHEST_PRECEDENCE)
        public void xiaoYuSleep(PayloadApplicationEvent<SpringEventModelOnSpringEvent.XiaoYu> event) {
            System.out.println("小明：讲一个爱情故事");
        }
    }

    /** 小明：最后执行 */
    @Component
    public static class XiaoMingLast {

        @EventListener(condition = "#event.source=='wakeUp'")
        @Order(Ordered.LOWEST_PRECEDENCE)
        public void xiaoYuWakeUp(PayloadApplicationEvent<SpringEventModelOnSpringEvent.XiaoYu> event) {
            System.out.println("小明：陪小玉一起上学");
        }

        @EventListener(condition = "#event.source=='sleep'")
        @Order(Ordered.LOWEST_PRECEDENCE)
        public void xiaoYuSleep(PayloadApplicationEvent<SpringEventModelOnSpringEvent.XiaoYu> event) {
            System.out.println("小明：我也睡了");
        }
    }

    public static void main(String[] args) {
        BeanFactory beanFactory = new AnnotationConfigApplicationContext(
                SpringEventModelOnSpringEvent.XiaoYu.class,
                SpringEventModelOnSpringEvent.XiaoMing.class,
                XiaoMingFirst.class,
                XiaoMingLast.class
        );
        SpringEventModelOnSpringEvent.XiaoYu xiaoYu = beanFactory.getBean(SpringEventModelOnSpringEvent.XiaoYu.class);
        xiaoYu.wakeUp();
        xiaoYu.sleep();
    }


}
