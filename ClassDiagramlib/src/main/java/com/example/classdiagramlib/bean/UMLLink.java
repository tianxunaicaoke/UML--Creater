package com.example.classdiagramlib.bean;

public abstract class UMLLink {
    String target;
    String origin;

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public abstract String toUMLString();
}
