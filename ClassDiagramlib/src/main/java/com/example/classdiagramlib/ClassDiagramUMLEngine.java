package com.example.classdiagramlib;

import com.example.classdiagramlib.exception.UMLAnnotationNotOnClassException;
import com.example.classdiagramlib.strategy.UMLStrategy;
import com.example.classdiagramlib.strategy.ClassUMLStrategy;
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
public class ClassDiagramUMLEngine extends AbstractProcessor {
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
        List<Element> list = new ArrayList<>(roundEnvironment.getElementsAnnotatedWith(IncludeClass.class));
        UMLStrategy UMLStrategy = new ClassUMLStrategy();
        try {
            UMLStrategy.createUML(list);
            UMLStrategy.generateFileAndPNG(filer);
        } catch (UMLAnnotationNotOnClassException e) {
            error(e.getElement(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
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
