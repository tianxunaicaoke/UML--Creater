package com.example.uml_create;


import com.example.classdiagramlib.annotation.IncludeClass;

@IncludeClass
public class B extends A {
    public C c;
    B() {
        c = new C();
    }
}
