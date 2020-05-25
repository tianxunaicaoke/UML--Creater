package com.example.uml_create;

import com.example.umlannotation.IncludeClass;

@IncludeClass(umlNode = "hello", umlPackage = "David1")
public class B extends A {
    public C c;
    B() {
        c = new C();
    }
}
