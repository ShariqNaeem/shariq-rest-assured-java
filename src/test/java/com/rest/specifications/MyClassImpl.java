package com.rest.specifications;

public class MyClassImpl implements MyInterface{

    private MyInterface myInterface;

    public MyInterface printMe() {
        System.out.println("PRINT_ME: This is my class method...");
        return myInterface;
    }
}
