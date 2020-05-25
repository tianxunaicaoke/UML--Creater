package com.example.classdiagramlib.bean;

import java.util.ArrayList;
import java.util.List;

import static com.example.classdiagramlib.fileCreater.Template.*;

public class UMLImplement extends UMLLink {
    List<String> targets = new ArrayList<>();
    public void addTarget(String name){
        targets.add(name);
    }
    public List<String> getTargets() {
        return targets;
    }

    @Override
    public String toUMLString() {
        StringBuilder stringBuffer = new StringBuilder(CLASS + origin);
        if (target != null) {
            stringBuffer.append(EXTENDS)
                    .append(target);
        }
        if (targets != null && !targets.isEmpty()) {
            stringBuffer.append(IMPLEMENT);
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
