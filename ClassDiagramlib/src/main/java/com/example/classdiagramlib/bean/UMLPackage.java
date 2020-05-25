package com.example.classdiagramlib.bean;

import java.util.List;

public class UMLPackage {
    String Name;
    List<UMLClass> classes;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public List<UMLClass> getClasses() {
        return classes;
    }

    public void setClasses(List<UMLClass> classes) {
        this.classes = classes;
    }
}
