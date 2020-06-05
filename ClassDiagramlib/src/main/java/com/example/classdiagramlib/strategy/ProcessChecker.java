package com.example.classdiagramlib.strategy;

import com.example.classdiagramlib.exception.UMLAnnotationNotOnRightPlaceException;

import javax.lang.model.element.Element;

import static javax.lang.model.element.ElementKind.METHOD;


public class ProcessChecker {
    public static boolean checkAnnotationIfOnClass(Element element) throws UMLAnnotationNotOnRightPlaceException {
        if (!element.getKind().isInterface() && !element.getKind().isClass()) {
            throw new UMLAnnotationNotOnRightPlaceException(element, "Annotation Only can be used on class");
        }
        return true;
    }

    public static boolean checkAnnotationIfOnMethod(Element element) throws UMLAnnotationNotOnRightPlaceException {
        if (!element.getKind().equals(METHOD)) {
            throw new UMLAnnotationNotOnRightPlaceException(element, "Annotation Only can be used on class");
        }
        return true;
    }
}
