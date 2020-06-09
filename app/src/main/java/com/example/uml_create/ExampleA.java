package com.example.uml_create;

import com.example.classdiagramlib.annotation.Step;

public class ExampleA {
    ExampleB b = new ExampleB();
    ExampleC c = new ExampleC();
    ExampleD d = new ExampleD();
    ExampleE e = new ExampleE();

    @Step(value = "ExampleB:init", divider = "init")
    @Step(value = "ExampleA:startWork",group = "Core part",divider = "start work")
    public void init() {
        b.init();
        startWork();
    }

    @Step("ExampleC:getAction")
    @Step("ExampleD:doAction")
    @Step("ExampleE:doFinish")
    public boolean startWork() {
        String action = c.getAction();
        d.doAction(action);
        e.doFinished();
        return true;
    }
}
