package com.template.design.Factory;

public class Grower {
    public static <T extends Fruits> T getFruits(Class<T> clz) {
        try {
            Fruits fruits = (Fruits) Class.forName(clz.getName()).newInstance();
            return (T) fruits;
        } catch (Exception e) {
            return null;
        }
    }
}
