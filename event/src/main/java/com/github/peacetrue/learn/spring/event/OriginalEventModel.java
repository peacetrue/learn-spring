package com.github.peacetrue.learn.spring.event;

import java.util.EventListener;
import java.util.EventObject;
import java.util.LinkedList;
import java.util.List;

/**
 * 【原始事件模型】
 * <p>
 * 小明和小玉住在一个村，小明很喜欢小玉，想要悉心照顾她。
 * 小明在小玉起床后和睡觉前，会为她做一些事。
 *
 * @author xiayx
 */
public class OriginalEventModel {

    /** 小玉 */
    public static class XiaoYu {

        private List<XiaoYuListener> xiaoYuListeners = new LinkedList<>();

        /** 早上起床了 */
        public void wakeUp() {
            System.out.println("小玉：起床了");
            xiaoYuListeners.forEach(xiaoYuListener -> xiaoYuListener.xiaoYuWakeUp(new XiaoYuEvent(this)));
        }

        /** 晚上睡觉了 */
        public void sleep() {
            System.out.println("小玉：睡觉了");
            xiaoYuListeners.forEach(xiaoYuListener -> xiaoYuListener.xiaoYuSleep(new XiaoYuEvent(this)));
        }

        public void addXiaoYuListener(XiaoYuListener xiaoYuListener) {
            this.xiaoYuListeners.add(xiaoYuListener);
        }
    }

    /** 小玉监听器 */
    public interface XiaoYuListener extends EventListener {
        void xiaoYuWakeUp(XiaoYuEvent event);

        void xiaoYuSleep(XiaoYuEvent event);
    }

    /** 小玉事件 */
    public static class XiaoYuEvent extends EventObject {
        public XiaoYuEvent(XiaoYu source) {
            super(source);
        }

        public XiaoYu getSource() {
            return (XiaoYu) super.getSource();
        }
    }

    /** 小明 */
    public static class XiaoMing {
        public void listenXiaoYu(XiaoYu xiaoYu) {
            xiaoYu.addXiaoYuListener(new XiaoYuListener() {
                public void xiaoYuWakeUp(XiaoYuEvent event) {
                    System.out.println("小明：给小玉准备早餐喽");
                }

                public void xiaoYuSleep(XiaoYuEvent event) {
                    System.out.println("小明：祝福小玉做个好梦");
                }
            });
        }
    }


    public static void main(String[] args) {
        XiaoYu xiaoYu = new XiaoYu();
        new XiaoMing().listenXiaoYu(xiaoYu);
        xiaoYu.wakeUp();
        xiaoYu.sleep();
    }

}
