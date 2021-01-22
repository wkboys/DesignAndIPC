package com.template.design.Factory;

public abstract class AGrower {
    /**
     * 获取水果
     *
     * @param clz
     *            具体水果类型
     * @return 具体水果的对象
     */
    public abstract <T extends Fruits> T getFruits(Class<T> clz);
}
