package com.github.peacetrue.learn.spring.event;

import com.github.peacetrue.learn.spring.event.SpringEventModelOnCustomEvent.XiaoYuSleepEvent;
import com.github.peacetrue.learn.spring.event.SpringEventModelOnCustomEvent.XiaoYuWakeUpEvent;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.PayloadApplicationEvent;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * 使用【spring 内置事件对象】简化开发。
 * <p>
 * 在【spring 事件模型】中，你需要定义{@link XiaoYuWakeUpEvent}和{@link XiaoYuSleepEvent}，
 * 你也可以直接使用 spring 提供的内置事件对象{@link PayloadApplicationEvent}，
 * 使用{@link PayloadApplicationEvent#getSource()}指定事件名，
 * 使用{@link PayloadApplicationEvent#getPayload()}指定事件源，
 * 这样就不用自己定义事件对象了。
 *
 * @author xiayx
 * @see OriginalEventModel
 */
public class SpringEventModelOnSpringEvent {

    /** 小玉 */
    @Component
    public static class XiaoYu {

        @Autowired
        private ApplicationEventPublisher eventPublisher;

        /** 早上起床了 */
        public void wakeUp() {
            System.out.println("小玉：起床了");
            eventPublisher.publishEvent(new PayloadApplicationEvent<>("wakeUp", this));
        }

        /** 晚上睡觉了 */
        public void sleep() {
            System.out.println("小玉：睡觉了");
            eventPublisher.publishEvent(new PayloadApplicationEvent<>("sleep", this));
        }
    }

    /** 小明 */
    @Component
    public static class XiaoMing {

        @EventListener(condition = "#event.source=='wakeUp'")
        public void xiaoYuWakeUp(PayloadApplicationEvent<XiaoYu> event) {
            System.out.println("小明：给小玉准备早餐喽");
        }

        @EventListener(condition = "#event.source=='sleep'")
        public void xiaoYuSleep(PayloadApplicationEvent<XiaoYu> event) {
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
