package com.example.classdiagramlib.strategy;

import com.example.classdiagramlib.exception.UMLAnnotationNotOnClassException;

import javax.lang.model.element.Element;

public class ProcessChecker {
    public static boolean checkAnnotationIfOnClass(Element element) throws UMLAnnotationNotOnClassException {
        if (!element.getKind().isInterface() && !element.getKind().isClass()) {
            throw new UMLAnnotationNotOnClassException(element, "Annotation Only can be used on class");
        }
        return true;
    }
}
