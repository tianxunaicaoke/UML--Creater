package com.example.classdiagramlib.bean;

import static com.example.classdiagramlib.fileCreater.Template.COLON;
import static com.example.classdiagramlib.fileCreater.Template.DIVIDER;
import static com.example.classdiagramlib.fileCreater.Template.INVOKE;
import static com.example.classdiagramlib.fileCreater.Template.LINE_FEED;
import static com.example.classdiagramlib.fileCreater.Template.NOTE_OVER;
import static com.example.classdiagramlib.fileCreater.Template.NOTE_TOP;
import static com.example.classdiagramlib.fileCreater.Template.noteTemplate;

public class UMLInvoke {
    String currentMethod;
    String preClassName;
    String className;
    String divider;
    String activate;
    String note;
    boolean hasReturnType;
    boolean needToDrawReturn;
    UMLInvoke next;

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

    public boolean isNeedToDrawReturn() {
        return needToDrawReturn;
    }

    public void setNeedToDrawReturn(boolean needToDrawReturn) {
        this.needToDrawReturn = needToDrawReturn;
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

    public UMLInvoke getNext() {
        return next;
    }

    public void setNext(UMLInvoke next) {
        this.next = next;
    }

    public String toUMLString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (getDivider() != null) {
            stringBuilder.append(DIVIDER)
                    .append(getDivider())
                    .append(DIVIDER)
                    .append(LINE_FEED);
        }
        if (getNote() != null) {
            stringBuilder.append(noteTemplate(getClassName(), getNote(), NOTE_OVER))
                    .append(LINE_FEED);
        }
         stringBuilder.append(getPreClassName() == null ? "" : getPreClassName())
                .append(INVOKE)
                .append(getClassName())
                .append(COLON)
                .append(getCurrentMethod());
        if (getActivate() != null) {
            stringBuilder.append(LINE_FEED)
                    .append(getActivate());
        }
        return stringBuilder.toString();
    }
}
