package com.example.classdiagramlib.fileCreater;


import java.util.List;

import javax.annotation.processing.Filer;

public interface UMLFileGenerator<T> {
    void setNode(List<T> list);

    void writeFile(Filer filer);

    void createPNG();
}
