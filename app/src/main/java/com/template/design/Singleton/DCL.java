package com.template.design.Singleton;

public class DCL {
    private static DCL sInstance = null;

    private DCL() {
    }

    public static DCL getInstance() {
        if (sInstance == null) {
            synchronized (DCL.class) {
                if (sInstance == null) {
                    sInstance = new DCL();
                }
            }
        }
        return sInstance;
    }
}

