package com.example.classdiagramlib.bean;

import static com.example.classdiagramlib.fileCreater.Template.Composition;

public class UMLAggregation extends UMLLink {
    @Override
    public String toUMLString() {
        return origin + Composition + target;
    }
}
