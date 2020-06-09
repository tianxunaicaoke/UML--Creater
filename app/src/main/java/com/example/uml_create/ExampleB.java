package com.example.uml_create;

import com.example.classdiagramlib.annotation.Step;

public class ExampleB {
    ExampleH h = new ExampleH();
    @Step("ExampleH:initEngine")
    public boolean init(){
      h.initEngine();
      return true;
    }
}
