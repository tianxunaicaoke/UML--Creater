package com.example.classdiagramlib.fileCreater;

import java.util.List;

public class Template {
    public static final String START = "@startuml";
    public static final String END = "@enduml";
    public static final String END_1 = "end";
    public static final String LINE_FEED = "\n";
    public static final String LINE_FEED_1 = "\\n";
    public static final String END_PRACKET = "}";
    public static final String START_PRACKET = "{";
    public static final String QUOTATION = "\"";
    public static final String CLASS = "class ";
    public static final String INTERFACE = "interface ";
    public static final String EXTENDS = " extends ";
    public static final String NODE = "node ";
    public static final String NOTE_TOP = "note top of ";
    public static final String NOTE_OVER = "note over ";
    public static final String PACKAGE = "package ";
    public static final String IMPLEMENT = " implements ";
    public static final String COMMA = " , ";
    public static final String COLON = " : ";
    public static final String SPACE = " ";
    public static final String COMPOSITION = " *-- ";
    public static final String INVOKE = " --> ";
    public static final String DIVIDER = "==";
    public static final String ACTIVATE = "activate ";
    public static final String DEACTIVATE = "deactivate ";
    public static final String RETURNED = " returned";
    public static final String GROUP = "group ";


    public static String classSuperTemplate(String target, String superClass, List<String> superInterface) {
        StringBuilder stringBuffer = new StringBuilder(CLASS + target);
        if (superClass != null) {
            stringBuffer.append(EXTENDS)
                    .append(superClass);
        }
        if (superInterface != null && !superInterface.isEmpty()) {
            stringBuffer.append(IMPLEMENT);
            for (int i = 0; i < superInterface.size(); i++) {
                stringBuffer.append(superInterface.get(i));
                if (i != superInterface.size() - 1) {
                    stringBuffer.append(COMMA);
                }
            }
        }
        return stringBuffer.toString();
    }

    @SuppressWarnings("unused")
    public static String classHoldTemplate(String target, String[] hold) {
        StringBuilder stringBuffer = new StringBuilder("");
        if (hold != null) {
            for (String s : hold) {
                stringBuffer.append("note 'hold' as")
                        .append(target).append(s)
                        .append(LINE_FEED)
                        .append(target)
                        .append(" --> ")
                        .append(target).append(s)
                        .append(LINE_FEED)
                        .append(target)
                        .append(s)
                        .append(" --> ")
                        .append(s);
            }
        }
        return stringBuffer.toString();
    }

    public static String interfaceSuperTemplate(String target, String[] superClass) {
        StringBuilder stringBuffer = new StringBuilder(INTERFACE + target);
        if (superClass != null && superClass.length > 0) {
            stringBuffer.append(EXTENDS);
            for (int i = 0; i < superClass.length; i++) {
                stringBuffer.append(superClass[i]);
                if (i != superClass.length - 1) {
                    stringBuffer.append(COMMA);
                }
            }
        }
        return stringBuffer.toString();
    }

    public static String noteTemplate(String mClass, String note, String notePosition) {
        String[] chars = note.split(" ");
        int size = 4;
        while (size < chars.length - 1) {
            chars[size] += LINE_FEED_1;
            size += 4;
        }

        StringBuilder out = new StringBuilder();
        for (String string :
                chars) {
            out.append(string).append(" ");
        }
        return notePosition + mClass + COLON + out;
    }

    public static String nodeTemplate(String nodeName) {
        return NODE +
                QUOTATION + nodeName + QUOTATION +
                START_PRACKET;
    }

    public static String packageTemplate(String packageName) {
        return PACKAGE +
                QUOTATION + packageName + QUOTATION +
                START_PRACKET;
    }
}