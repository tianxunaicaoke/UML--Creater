package com.example.classdiagramlib.strategy;


import java.util.List;

import javax.annotation.processing.Filer;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;

public interface UMLStrategy {
    void createUML(List<Element> elements) throws Exception;
    void generateFileAndPNG(Filer filer);
}
