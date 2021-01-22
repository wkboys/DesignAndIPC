package com.template.design.Factory;

public class Banana extends Fruits {
    @Override
    public void color() {
        System.out.println("Banana is red");
    }

    @Override
    public void weight() {
        System.out.println("Weight 0.3kg");
    }
}
