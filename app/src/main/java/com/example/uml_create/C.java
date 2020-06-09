package com.example.uml_create;

import com.example.classdiagramlib.annotation.IncludeClass;
import com.example.classdiagramlib.annotation.Step;

@IncludeClass
public class C {
    D d = new D();

    public int f(){
     return 1;
    }

    public void f1(){

    }

    public void f2(){

    }

    public void f3(){
        d.f1();
    }


    public void f4(){

    }
}
