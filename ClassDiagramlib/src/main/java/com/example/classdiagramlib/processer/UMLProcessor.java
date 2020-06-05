package com.example.classdiagramlib.processer;


import java.util.List;

import javax.lang.model.element.Element;

public interface UMLProcessor<T> {
    void configUMLElement(List<Element> elements, List<T> nodes) throws Exception;

    void buildUMLGraph(List<T> nodes);
}
