package com.template.design.Factory;

public class Cucumber extends Fruits {
    @Override
    public void color() {
        System.out.println("Cucumber is green");
    }

    @Override
    public void weight() {
        System.out.println("Weight 0.5kg");
    }
}
