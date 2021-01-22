package com.template.design.Singleton;

public class Hungry {
    // 默认构造方法 私有化 不让外界调用
    private Hungry() {}
    private static final Hungry SINGLE = new Hungry();
    public static Hungry getSingle() {
        return SINGLE;
    }
}
