package com.template.design.Singleton;

public class Static {
    private Static() { }
    public static Static getInstance () {
        return SingletonHolder.sInstance;
    }
    //静态内部类
    private static class SingletonHolder {
        private static final Static sInstance = new Static();
    }
}