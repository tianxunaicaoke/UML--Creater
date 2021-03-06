package com.example.classdiagramlib.strategy;

import com.example.classdiagramlib.bean.UMLNode;
import com.example.classdiagramlib.fileCreater.ClassFileGenerator;
import com.example.classdiagramlib.fileCreater.UMLFileGenerator;
import com.example.classdiagramlib.processer.ClassUMLProcessor;
import com.example.classdiagramlib.processer.UMLProcessor;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Element;

public class ClassUMLStrategy implements UMLStrategy{

    private UMLFileGenerator umlFileGenerator;
    private UMLProcessor umlProcessor;
    private List<UMLNode> nodes = new ArrayList<>();

    public ClassUMLStrategy(){
        umlFileGenerator = new ClassFileGenerator();
        umlProcessor = new ClassUMLProcessor();
    }

    @Override
    public void createUML(List<Element> elements) throws Exception {
        umlProcessor.configUMLElement(elements,nodes);
        umlProcessor.buildUMLGraph(nodes);
    }

    @Override
    public void generateFileAndPNG(Filer filer) {
        umlFileGenerator.setNode(nodes);
        umlFileGenerator.writeFile(filer);
        umlFileGenerator.createPNG();
    }
}
