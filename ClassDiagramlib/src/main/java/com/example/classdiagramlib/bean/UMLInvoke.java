package com.example.classdiagramlib.bean;

import java.util.ArrayList;
import java.util.List;

import static com.example.classdiagramlib.fileCreater.Template.COLON;
import static com.example.classdiagramlib.fileCreater.Template.DEACTIVATE;
import static com.example.classdiagramlib.fileCreater.Template.DIVIDER;
import static com.example.classdiagramlib.fileCreater.Template.GROUP;
import static com.example.classdiagramlib.fileCreater.Template.INVOKE;
import static com.example.classdiagramlib.fileCreater.Template.LINE_FEED;
import static com.example.classdiagramlib.fileCreater.Template.NOTE_OVER;
import static com.example.classdiagramlib.fileCreater.Template.RETURNED;
import static com.example.classdiagramlib.fileCreater.Template.noteTemplate;

public class UMLInvoke {
    String currentMethod;
    String preClassName;
    String preMethod;
    String className;
    String divider;
    String activate;
    String note;
    String group;
    boolean hasReturnType;
    List<UMLInvoke> invokes = new ArrayList<>();

    public void setInvokes(List<UMLInvoke> invokes){
        this.invokes = invokes;
    }

    public void addInvoke(UMLInvoke umlInvoke){
        invokes.add(umlInvoke);
    }

    public List<UMLInvoke> getInvokes(){
        return invokes;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getActivate() {
        return activate;
    }

    public void setActivate(String activate) {
        this.activate = activate;
    }

    public boolean isHasReturnType() {
        return hasReturnType;
    }

    public void setHasReturnType(boolean hasReturnType) {
        this.hasReturnType = hasReturnType;
    }

    public String getPreMethod() {
        return preMethod;
    }

    public void setPreMethod(String preMethod) {
        this.preMethod = preMethod;
    }

    public String getCurrentMethod() {
        return currentMethod;
    }

    public void setCurrentMethod(String currentMethod) {
        this.currentMethod = currentMethod;
    }

    public String getDivider() {
        return divider;
    }

    public void setDivider(String divider) {
        this.divider = divider;
    }

    public String getPreClassName() {
        return preClassName;
    }

    public void setPreClassName(String preClassName) {
        this.preClassName = preClassName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String toUMLString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (getDivider() != null) {
            stringBuilder.append(DIVIDER)
                    .append(getDivider())
                    .append(DIVIDER)
                    .append(LINE_FEED);
        }
        if(getGroup()!=null){
            stringBuilder.append(GROUP)
                    .append(getGroup())
                    .append(LINE_FEED);
        }
        if (getNote() != null) {
            stringBuilder.append(noteTemplate(getClassName(), getNote(), NOTE_OVER))
                    .append(LINE_FEED);
        }
        //TODO will fix it later
        if(getClassName() == null){
            stringBuilder.append(" ")
                    .append(INVOKE)
                    .append(getPreClassName())
                    .append(COLON)
                    .append(getPreMethod());
        }else{
            stringBuilder.append(getPreClassName() == null ? "" : getPreClassName())
                    .append(INVOKE)
                    .append(getClassName())
                    .append(COLON)
                    .append(getCurrentMethod());
        }
        if (getActivate() != null) {
            stringBuilder.append(LINE_FEED)
                    .append(getActivate());
        }
        return stringBuilder.toString();
    }

    public String toReturnString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getClassName())
                .append(INVOKE)
                .append(getPreClassName())
                .append(COLON)
                .append(getCurrentMethod() + RETURNED)
                .append(LINE_FEED)
                .append(DEACTIVATE + getClassName());
        return stringBuilder.toString();
    }
}
