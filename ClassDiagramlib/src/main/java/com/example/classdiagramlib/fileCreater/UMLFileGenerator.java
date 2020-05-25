package com.example.classdiagramlib.fileCreater;

import com.example.classdiagramlib.bean.UMLNode;

import java.util.List;

import javax.annotation.processing.Filer;

public interface UMLFileGenerator {
    void setNode(List<UMLNode> list);

    void writeToFile(Filer filer);
}
