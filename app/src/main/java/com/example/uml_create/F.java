package com.example.uml_create;

import com.example.umlannotation.IncludeClass;

@IncludeClass(umlNode = "hello",umlPackage = "world")
public class F implements AI {
    B b;
    F(){
       b = new B();
    }
}
