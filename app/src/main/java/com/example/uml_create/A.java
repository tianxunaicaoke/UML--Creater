package com.example.uml_create;

import com.example.classdiagramlib.annotation.IncludeClass;

@IncludeClass
public class A implements AI{
    public C c = new C();

    public void f2(){
        f3();
    }

    public void f3(){
        f4();
        f5();
        f1(true);
    }


    public void f4(){

    }
    public void f5(){

    }


    public void f1(boolean flag){
        if(flag){
            c.f();
        }else{
            c.f1();
        }

        if(flag){
          c.f2();
          c.f3();
        }else{
            c.f4();
        }

    }
}
