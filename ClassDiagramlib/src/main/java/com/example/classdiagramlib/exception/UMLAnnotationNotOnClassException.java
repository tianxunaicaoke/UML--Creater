package com.example.classdiagramlib.exception;

import javax.lang.model.element.Element;

public class UMLAnnotationNotOnClassException extends Exception {
    Element element;

    public UMLAnnotationNotOnClassException(Element element, String msg, Object... args) {
        super(String.format(msg, args));
        this.element = element;
    }

    public Element getElement() {
        return element;
    }
}
