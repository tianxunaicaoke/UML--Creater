package com.example.classdiagramlib.bean;

import static com.example.classdiagramlib.fileCreater.Template.Composition;

public class UMLComposition extends UMLLink {
    @Override
    public String toUMLString() {
        return getOrigin() + Composition + getTarget();
    }
}
