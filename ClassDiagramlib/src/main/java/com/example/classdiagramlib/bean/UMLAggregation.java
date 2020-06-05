package com.example.classdiagramlib.bean;

import static com.example.classdiagramlib.fileCreater.Template.COMPOSITION;

public class UMLAggregation extends UMLLink {
    @Override
    public String toUMLString() {
        return origin + COMPOSITION + target;
    }
}
