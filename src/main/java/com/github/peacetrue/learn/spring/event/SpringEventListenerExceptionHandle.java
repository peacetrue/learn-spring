package com.github.peacetrue.learn.spring.event;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.PayloadApplicationEvent;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 【spring 事件异常处理】
 * <p>
 * 小明在做事情时发生了异常，不应该让小玉感知到；
 * 小明做的多项事情之间也不应该相互影响。
 * <p>
 * spring 没有处理事件的异常，如果事件执行发生异常，此异常会向外抛出，导致后续事件无法执行。
 * 基于程序稳定性考虑，事件处理过程中都不应该发生异常，如果发生异常，要自己捕获住。
 *
 * @author xiayx
 */
public class SpringEventListenerExceptionHandle {

    /** 小明：异常导致后续事件无法执行 */
    @Component
    public static class XiaoMingException {

        @EventListener(condition = "#event.source=='wakeUp'")
        @Order(Ordered.HIGHEST_PRECEDENCE)
        public void xiaoYuWakeUp(PayloadApplicationEvent<SpringEventModelOnSpringEvent.XiaoYu> event) {
            throw new IllegalStateException("哎，起晚了");
        }

        @EventListener(condition = "#event.source=='sleep'")
        @Order(Ordered.HIGHEST_PRECEDENCE)
        public void xiaoYuSleep(PayloadApplicationEvent<SpringEventModelOnSpringEvent.XiaoYu> event) {
            throw new IllegalStateException("哎，睡早了");
        }
    }

    /** 小明：捕获住异常不影响后续执行 */
    @Component
    public static class XiaoMingCatchException {

        @EventListener(condition = "#event.source=='wakeUp'")
        @Order(Ordered.HIGHEST_PRECEDENCE)
        public void xiaoYuWakeUp(PayloadApplicationEvent<SpringEventModelOnSpringEvent.XiaoYu> event) {
            try {
                throw new IllegalStateException("哎，起晚了");
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        }

        @EventListener(condition = "#event.source=='sleep'")
        @Order(Ordered.HIGHEST_PRECEDENCE)
        public void xiaoYuSleep(PayloadApplicationEvent<SpringEventModelOnSpringEvent.XiaoYu> event) {
            try {
                throw new IllegalStateException("哎，睡早了");
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        }
    }

    /** 小明：最后执行 */
    @Component
    public static class XiaoMingLast {

        @EventListener(condition = "#event.source=='wakeUp'")
        @Order(Ordered.LOWEST_PRECEDENCE)
        public void xiaoYuWakeUp(PayloadApplicationEvent<String> event) {
            System.out.println("小明：陪小玉一起上学");
        }

        @EventListener(condition = "#event.source=='sleep'")
        @Order(Ordered.LOWEST_PRECEDENCE)
        public void xiaoYuSleep(PayloadApplicationEvent<String> event) {
            System.out.println("小明：我也睡了");
        }

    }

    public static void main(String[] args) {
        BeanFactory beanFactory = new AnnotationConfigApplicationContext(
                SpringEventModelOnSpringEvent.XiaoYu.class,
                SpringEventModelOnSpringEvent.XiaoMing.class,
                XiaoMingException.class,
                XiaoMingLast.class
        );
        SpringEventModelOnSpringEvent.XiaoYu xiaoYu = beanFactory.getBean(SpringEventModelOnSpringEvent.XiaoYu.class);
        xiaoYu.wakeUp();
        xiaoYu.sleep();
    }


}
