package com.template.design.Singleton;

public class Lazy {
    private static Lazy instance;
    private Lazy () {}
    public static synchronized Lazy getInstance() {
        if (instance == null) {
            instance = new Lazy ();
        }
        return instance;
    }
}
