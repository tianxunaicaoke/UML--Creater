package com.example.classdiagramlib.strategy;

import com.example.classdiagramlib.exception.UMLAnnotationNotOnClassException;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;

public class ProcessChecker {
    public static boolean checkAnnotationIfOnClass(Element element) throws UMLAnnotationNotOnClassException {
        if (element.getKind() != ElementKind.CLASS && element.getKind() != ElementKind.INTERFACE) {
            throw new UMLAnnotationNotOnClassException(element, "Annotation Only can be used on class");
        }
        return true;
    }
}
