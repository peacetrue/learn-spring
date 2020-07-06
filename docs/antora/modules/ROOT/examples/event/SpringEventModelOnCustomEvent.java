package com.github.peacetrue.learn.spring.event;

import com.github.peacetrue.learn.spring.event.OriginalEventModel.XiaoYuEvent;
import com.github.peacetrue.learn.spring.event.OriginalEventModel.XiaoYuListener;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.EventObject;

/**
 * 基于【原始事件模型】实现的【spring 事件模型】。
 * <p>
 * 对比【原始事件模型】，【spring 事件模型】少了一个监听器（{@link XiaoYuListener}），
 * 事件（{@link XiaoYuEvent}）被拆分成了{@link XiaoYuWakeUpEvent}和{@link XiaoYuSleepEvent}。
 * 【原始事件模型】通过监听器的方法区别不同的事件，【spring 事件模型】通过事件对象区别不同的事件。
 *
 * @author xiayx
 * @see OriginalEventModel
 */
public class SpringEventModelOnCustomEvent {

    /** 小玉起床事件 */
    public static class XiaoYuWakeUpEvent extends EventObject {
        public XiaoYuWakeUpEvent(XiaoYu source) {
            super(source);
        }

        public XiaoYu getSource() {
            return (XiaoYu) super.getSource();
        }
    }

    /** 小玉睡觉事件 */
    public static class XiaoYuSleepEvent extends EventObject {
        public XiaoYuSleepEvent(XiaoYu source) {
            super(source);
        }

        public XiaoYu getSource() {
            return (XiaoYu) super.getSource();
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
            eventPublisher.publishEvent(new XiaoYuWakeUpEvent(this));
        }

        /** 晚上睡觉了 */
        public void sleep() {
            System.out.println("小玉：睡觉了");
            eventPublisher.publishEvent(new XiaoYuSleepEvent(this));
        }
    }

    /** 小明 */
    @Component
    public static class XiaoMing {

        @EventListener
        public void xiaoYuWakeUp(XiaoYuWakeUpEvent event) {
            System.out.println("小明：给小玉准备早餐喽");
        }

        @EventListener
        public void xiaoYuSleep(XiaoYuSleepEvent event) {
            System.out.println("小明：祝福小玉做个好梦");
        }
    }


    public static void main(String[] args) {
        BeanFactory beanFactory = new AnnotationConfigApplicationContext(XiaoYu.class, XiaoMing.class);
        XiaoYu xiaoYu = beanFactory.getBean(XiaoYu.class);
        xiaoYu.wakeUp();
        xiaoYu.sleep();
    }

}
