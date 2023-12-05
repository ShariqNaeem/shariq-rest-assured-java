package com.rest.specifications;

public class MyMainClass {
    public static void main(String[] args) {

        //Example For Request Specification
        MyInterface myInterface = new MyClassImpl();
        myInterface.printMe();
    }
}
