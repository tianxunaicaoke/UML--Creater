package com.example.classdiagramlib.bean;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Element;

public class UMLClass {
    private String name;
    private String fullName;
    private Element element;
    private List<UMLLink> links = new ArrayList<>();
    private List<UMLNote> notes = new ArrayList<>();

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addLink(UMLLink link) {
        links.add(link);
    }

    public void addnote(UMLNote note) {
        notes.add(note);
    }

    public Element getElement() {
        return element;
    }

    public void setElement(Element element) {
        this.element = element;
    }

    public List<UMLLink> getLinks() {
        return links;
    }

    public List<UMLNote> getNotes() {
        return notes;
    }
}
