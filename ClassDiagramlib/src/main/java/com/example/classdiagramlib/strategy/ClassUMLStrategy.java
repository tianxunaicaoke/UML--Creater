package com.example.classdiagramlib.strategy;

import com.example.classdiagramlib.bean.UMLNode;
import com.example.classdiagramlib.fileCreater.TxtFileGenerator;
import com.example.classdiagramlib.fileCreater.UMLFileGenerator;
import com.example.classdiagramlib.processer.ClassUMLProcessor;
import com.example.classdiagramlib.processer.UMLProcessor;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Element;

public class ClassUMLStrategy implements UMLStrategy {
    public static final TxtFileGenerator UML_FILE_GENERATOR = new TxtFileGenerator();
    private UMLFileGenerator umlFileGenerator;
    private UMLProcessor umlProcessor;
    private List<UMLNode> nodes = new ArrayList<>();

    public ClassUMLStrategy() {
        umlFileGenerator = UML_FILE_GENERATOR;
        umlProcessor = new ClassUMLProcessor();
    }

    @Override
    public void createUML(List<Element> elements) throws Exception {
        umlProcessor.configUMLElement(elements,nodes);
        umlProcessor.buildGraph(nodes);
        umlFileGenerator.setNode(nodes);
    }

    @Override
    public void generateToFile(Filer filer) {
        umlFileGenerator.writeToFile(filer);
    }
}
