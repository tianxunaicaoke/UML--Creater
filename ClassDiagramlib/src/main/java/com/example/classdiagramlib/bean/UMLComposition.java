package com.example.classdiagramlib.bean;

import static com.example.classdiagramlib.fileCreater.Template.COMPOSITION;

public class UMLComposition extends UMLLink {
    @Override
    public String toUMLString() {
        return getOrigin() + COMPOSITION + getTarget();
    }
}
