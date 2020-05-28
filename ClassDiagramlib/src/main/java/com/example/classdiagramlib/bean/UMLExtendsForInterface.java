package com.example.classdiagramlib.bean;

import java.util.ArrayList;
import java.util.List;

import static com.example.classdiagramlib.fileCreater.Template.COMMA;
import static com.example.classdiagramlib.fileCreater.Template.EXTENDS;
import static com.example.classdiagramlib.fileCreater.Template.INTERFACE;

public class UMLExtendsForInterface extends UMLLink{
    private List<String> targets = new ArrayList<>();
    public void addTarget(String name){
        targets.add(name);
    }
    @Override
    public String toUMLString() {
        StringBuilder stringBuffer = new StringBuilder(INTERFACE + origin);
        if (targets != null && !targets.isEmpty()) {
            stringBuffer.append(EXTENDS);
            for (int i = 0; i < targets.size(); i++) {
                stringBuffer.append(targets.get(i));
                if (i != targets.size() - 1) {
                    stringBuffer.append(COMMA);
                }
            }
        }
        return stringBuffer.toString();
    }
}
