package com.rest;

public class MethodChaining {

    public static void main(String[] args) {

        MethodChaining obj = new MethodChaining();
        //without method chaining
        obj.test1();
        obj.test2();
        obj.test2();

        // method chaining or builder pattern
            obj.
                method1().
                method2().
                method3();
    }

    public void test1(){
        System.out.println("this is test1 method");
    }

    public void test2(){
        System.out.println("this is test2 method");
    }

    public void test3(){
        System.out.println("this is test3 method");
    }

    public MethodChaining method1(){
        System.out.println("this is method1");
        return this;
    }

    public MethodChaining method2(){
        System.out.println("this is method2");
        return this;
    }

    public MethodChaining method3(){
        System.out.println("this is method3");
        return this;
    }
}
