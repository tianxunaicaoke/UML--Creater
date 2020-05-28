package com.example.uml_create;


import com.example.classdiagramlib.annotation.IncludeClass;

@IncludeClass
public class F implements AI {
    B b;
    C c;
    A a;
    F(){
       b = new B();
    }
}
