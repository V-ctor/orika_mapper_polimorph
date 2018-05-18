package com.victor;


public class Derived1 implements Base {
    private String message = "some message";

    public Derived1(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
