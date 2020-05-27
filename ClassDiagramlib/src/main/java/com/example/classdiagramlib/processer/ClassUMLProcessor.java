package com.example.classdiagramlib.processer;

import com.example.classdiagramlib.bean.UMLClass;
import com.example.classdiagramlib.bean.UMLComposition;
import com.example.classdiagramlib.bean.UMLExtendsForClass;
import com.example.classdiagramlib.bean.UMLExtendsForInterface;
import com.example.classdiagramlib.bean.UMLLink;
import com.example.classdiagramlib.bean.UMLNode;
import com.example.classdiagramlib.bean.UMLNote;
import com.example.classdiagramlib.bean.UMLPackage;
import com.example.classdiagramlib.strategy.ProcessChecker;
import com.example.classdiagramlib.util.ProcessorUtil;
import com.example.umlannotation.IncludeClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;


public class ClassUMLProcessor implements UMLProcessor {
    HashMap<String, UMLClass> exitClass = new HashMap<>();
    private static final String OBJECT = "java.lang.Object";

    @Override
    public void configUMLElement(List<Element> elements, List<UMLNode> nodes) throws Exception {
        for (Element element : elements) {
            ProcessChecker.checkAnnotationIfOnClass(element);
            IncludeClass annotation = element.getAnnotation(IncludeClass.class);
            boolean flag = false;
            for (UMLNode node : nodes) {
                if (node.getName().equals(annotation.umlNode())) {
                    convertInSameNode(element, node);
                    flag = true;
                }
            }
            if (!flag) {
                convertInNewNode(element, nodes);
            }
        }
    }

    @Override
    public void buildGraph(List<UMLNode> nodes) {
        if (exitClass != null && !exitClass.entrySet().isEmpty()) {
            for (UMLClass umlClass : exitClass.values()) {
                TypeElement typeElement = ((TypeElement) umlClass.getElement());
                if (typeElement.getKind().isClass()) {
                    UMLLink umlExtend = getExtendsUMLLinkForClass(typeElement);
                    if (umlExtend != null) {
                        umlExtend.setOrigin(umlClass.getName());
                        umlClass.addLink(umlExtend);

                    }
                } else if (typeElement.getKind().isInterface()) {
                    UMLLink umlExtend = getExtendsUMLLinkForInterface(typeElement);
                    if (umlExtend != null) {
                        umlExtend.setOrigin(umlClass.getName());
                        umlClass.addLink(umlExtend);
                    }
                }
                updateCompositionUMLLinkForInterface(umlClass, typeElement);
            }
        }
    }

    private void updateCompositionUMLLinkForInterface(UMLClass umlClass, TypeElement typeElement) {
        List<? extends Element> fields = typeElement.getEnclosedElements();
        for (Element item : fields) {
            if (exitClass.containsKey(item.asType().toString())) {
                UMLComposition composition = new UMLComposition();
                composition.setOrigin(umlClass.getName());
                composition.setTarget(ProcessorUtil.getSimpleName(item.asType().toString()));
                umlClass.addLink(composition);
            }
        }
    }

    private UMLLink getExtendsUMLLinkForInterface(TypeElement typeElement) {
        UMLExtendsForInterface umlExtend = null;
        List<? extends TypeMirror> superInterfaces = typeElement.getInterfaces();
        for (TypeMirror superI : superInterfaces) {
            if (umlExtend == null) {
                umlExtend = new UMLExtendsForInterface();
            }
            umlExtend.addTarget(ProcessorUtil.getSimpleName(superI.toString()));
        }
        return umlExtend;
    }

    private UMLLink getExtendsUMLLinkForClass(TypeElement typeElement) {
        UMLExtendsForClass umlExtend = null;
        TypeMirror superClass = typeElement.getSuperclass();
        if (!superClass.toString().equals(OBJECT)) {
            umlExtend = new UMLExtendsForClass();
            umlExtend.setTarget(ProcessorUtil.getSimpleName(superClass.toString()));
        }
        List<? extends TypeMirror> superInterfaces = typeElement.getInterfaces();
        for (TypeMirror superI : superInterfaces) {
            if (umlExtend == null) {
                umlExtend = new UMLExtendsForClass();
            }
            umlExtend.addTarget(ProcessorUtil.getSimpleName(superI.toString()));
        }
        return umlExtend;
    }

    private void convertInSameNode(Element element, UMLNode node) {
        IncludeClass annotation = element.getAnnotation(IncludeClass.class);
        boolean flag = false;
        for (UMLPackage umlPackage : node.getPackages()) {
            if (umlPackage.getName().equals(annotation.umlPackage())) {
                convertInSamePackage(element, umlPackage);
                flag = true;
            }
        }
        if (!flag) {
            convertInNewPackage(element, node.getPackages());
        }
    }

    private void convertInNewNode(Element element, List<UMLNode> nodes) {
        UMLNode umlNode = new UMLNode();
        IncludeClass annotation = element.getAnnotation(IncludeClass.class);
        List<UMLPackage> packages = new ArrayList<>();
        convertInNewPackage(element, packages);
        umlNode.setPackages(packages);
        umlNode.setName(annotation.umlNode());
        nodes.add(umlNode);
    }

    private void convertInSamePackage(Element element, UMLPackage umlPackage) {
        List<UMLClass> classes = umlPackage.getClasses();
        classes.add(convert(element));
    }

    private void convertInNewPackage(Element element, List<UMLPackage> umlPackages) {
        IncludeClass annotation = element.getAnnotation(IncludeClass.class);
        UMLPackage umlPackage = new UMLPackage();
        umlPackage.setName(annotation.umlPackage());
        List<UMLClass> classes = new ArrayList<>();
        classes.add(convert(element));
        umlPackage.setClasses(classes);
        umlPackages.add(umlPackage);
    }

    private UMLClass convert(Element element) {
        UMLClass umlClass = new UMLClass();
        TypeElement elementType = (TypeElement) element;
        IncludeClass annotation = element.getAnnotation(IncludeClass.class);
        if (!annotation.umlNote().equals("")) {
            UMLNote note = new UMLNote();
            note.setNote(annotation.umlNote());
            umlClass.addnote(note);
        }
        umlClass.setName(elementType.getSimpleName().toString());
        umlClass.setFullName(elementType.getQualifiedName().toString());
        umlClass.setElement(element);
        exitClass.put(umlClass.getFullName(), umlClass);
        return umlClass;
    }
}
