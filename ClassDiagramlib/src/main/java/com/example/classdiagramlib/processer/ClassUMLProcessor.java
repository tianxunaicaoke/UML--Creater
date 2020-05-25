package com.example.classdiagramlib.processer;

import com.example.classdiagramlib.bean.UMLClass;
import com.example.classdiagramlib.bean.UMLComposition;
import com.example.classdiagramlib.bean.UMLImplement;
import com.example.classdiagramlib.bean.UMLNode;
import com.example.classdiagramlib.bean.UMLPackage;
import com.example.classdiagramlib.classLoader.DynamicClassPathLoader;
import com.example.classdiagramlib.strategy.ProcessChecker;
import com.example.umlannotation.IncludeClass;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

public class ClassUMLProcessor implements UMLProcessor {
    HashMap<String, UMLClass> exitClass = new HashMap<>();

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
        String[] classPath = {"F:\\MAnnotation-master\\UMLCreate\\app\\temmp\\"};
        DynamicClassPathLoader classPathLoader = new DynamicClassPathLoader(classPath);
        classPathLoader.loadClass();
        if (exitClass != null && !exitClass.entrySet().isEmpty()) {
            Iterator<UMLClass> iterator = exitClass.values().iterator();
            while (iterator.hasNext()) {
                UMLClass umlClass = iterator.next();
                try {
                    UMLImplement umlExtend = null;
                    Class c = this.getClass().getClassLoader().loadClass(umlClass.getFullName());
                    Class superClass = c.getSuperclass();
                    if (!superClass.getSimpleName().equals("Object")) {
                        umlExtend = new UMLImplement();
                        umlExtend.setOrigin(umlClass.getName());
                        umlExtend.setTarget(superClass.getSimpleName());
                    }
                    Class<?>[] superInterfaces = c.getInterfaces();
                    for (Class superI : superInterfaces) {
                        if (umlExtend == null) {
                            umlExtend = new UMLImplement();
                            umlExtend.setOrigin(umlClass.getName());
                        }
                        umlExtend.addTarget(superI.getSimpleName());
                    }
                    if (umlExtend != null) {
                        umlClass.addLink(umlExtend);
                    }

                    Field[] holdClasses = c.getFields();
                    for (Field item : holdClasses) {
                        if (exitClass.containsKey(item.getType().getCanonicalName())) {
                            UMLComposition composition = new UMLComposition();
                            composition.setOrigin(umlClass.getName());
                            composition.setTarget(item.getType().getSimpleName());
                            umlClass.addLink(composition);
                        }
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
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
        umlClass.setName(elementType.getSimpleName().toString());
        umlClass.setFullName(elementType.getQualifiedName().toString());
        exitClass.put(umlClass.getFullName(), umlClass);
        return umlClass;
    }
}
