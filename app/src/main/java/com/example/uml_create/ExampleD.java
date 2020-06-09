package com.example.uml_create;


import com.example.classdiagramlib.annotation.Step;

public class ExampleD {
    ExampleF f = new ExampleF();

    @Step(value = "ExampleF:doOnce",note = "this action is in another thread")
    public boolean doAction(String action){
        f.doOnce();
        return true;
    }
}
