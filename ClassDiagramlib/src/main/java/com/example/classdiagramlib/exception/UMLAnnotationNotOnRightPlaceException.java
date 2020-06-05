package com.example.classdiagramlib.exception;

import javax.lang.model.element.Element;

public class UMLAnnotationNotOnRightPlaceException extends Exception {
    Element element;

    public UMLAnnotationNotOnRightPlaceException(Element element, String msg, Object... args) {
        super(String.format(msg, args));
        this.element = element;
    }

    public Element getElement() {
        return element;
    }
}
