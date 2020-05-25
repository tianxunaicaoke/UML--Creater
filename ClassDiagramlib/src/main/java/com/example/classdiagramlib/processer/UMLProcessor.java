package com.example.classdiagramlib.processer;

import com.example.classdiagramlib.bean.UMLNode;

import java.util.List;

import javax.lang.model.element.Element;

public interface UMLProcessor {
    void configUMLElement(List<Element> elements, List<UMLNode> nodes) throws Exception;

    void buildGraph(List<UMLNode> nodes);
}
