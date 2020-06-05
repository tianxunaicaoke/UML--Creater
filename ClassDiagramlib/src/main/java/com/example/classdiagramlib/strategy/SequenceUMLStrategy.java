package com.example.classdiagramlib.strategy;

import com.example.classdiagramlib.bean.UMLInvoke;
import com.example.classdiagramlib.bean.UMLNode;
import com.example.classdiagramlib.fileCreater.ClassFileGenerator;
import com.example.classdiagramlib.fileCreater.SequenceFileGenerator;
import com.example.classdiagramlib.fileCreater.UMLFileGenerator;
import com.example.classdiagramlib.processer.SequenceUMLProcessor;
import com.example.classdiagramlib.processer.UMLProcessor;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Element;

public class SequenceUMLStrategy implements UMLStrategy {
    private UMLFileGenerator umlFileGenerator;
    private UMLProcessor umlProcessor;
    private List<UMLInvoke> nodes = new ArrayList<>();

    public SequenceUMLStrategy() {
        umlFileGenerator = new SequenceFileGenerator();
        umlProcessor = new SequenceUMLProcessor();
    }

    @Override
    public void createUML(List<Element> elements) throws Exception {
        umlProcessor.configUMLElement(elements, nodes);
        umlProcessor.buildUMLGraph(nodes);
    }

    @Override
    public void generateFileAndPNG(Filer filer) {
        umlFileGenerator.setNode(nodes);
        umlFileGenerator.writeFile(filer);
        umlFileGenerator.createPNG();
    }
}
