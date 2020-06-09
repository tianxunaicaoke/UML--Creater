package com.example.classdiagramlib;

import com.example.classdiagramlib.annotation.Condition.*;
import com.example.classdiagramlib.annotation.Steps;
import com.example.classdiagramlib.annotation.Step;
import com.example.classdiagramlib.exception.UMLAnnotationNotOnRightPlaceException;
import com.example.classdiagramlib.strategy.SequenceUMLStrategy;
import com.example.classdiagramlib.strategy.ClassUMLStrategy;
import com.example.classdiagramlib.strategy.UMLStrategy;
import com.example.classdiagramlib.annotation.IncludeClass;
import com.google.auto.service.AutoService;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

@AutoService(Processor.class)
public class DiagramUMLEngine extends AbstractProcessor {
    private Filer filer;
    private Messager messager;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        filer = processingEnvironment.getFiler();
        messager = processingEnvironment.getMessager();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        if(roundEnvironment.processingOver()){
            return true;
        }

        List<Element> classList = new ArrayList<>(roundEnvironment.getElementsAnnotatedWith(IncludeClass.class));
        List<Element> sequenceList = new ArrayList<>(roundEnvironment.getElementsAnnotatedWith(Steps.class));
        sequenceList.addAll(roundEnvironment.getElementsAnnotatedWith(Step.class));
        if(!classList.isEmpty()){
            processDiagram(classList,new ClassUMLStrategy());
        }
        if(!sequenceList.isEmpty()){
            processDiagram(sequenceList,new SequenceUMLStrategy());
        }
        return true;
    }

    private void processDiagram(List<Element> list, UMLStrategy umlStrategy){
        try {
            umlStrategy.createUML(list);
            umlStrategy.generateFileAndPNG(filer);
        } catch (UMLAnnotationNotOnRightPlaceException e) {
            error(e.getElement(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotations  = new LinkedHashSet<>();
        annotations.add(IncludeClass.class.getCanonicalName());
        return annotations;
    }


    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    private void error(Element e, String msg, Object... args) {
        messager.printMessage(Diagnostic.Kind.ERROR, String.format(msg, args), e);
    }
}
