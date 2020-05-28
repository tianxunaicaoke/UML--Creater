package com.example.classdiagramlib.util;

public class ProcessorUtil {
    public static String getSimpleName(String s){
        return s.split("\\.")[s.split("\\.").length-1];
    }
}
