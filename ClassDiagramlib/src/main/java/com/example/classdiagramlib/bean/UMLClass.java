package com.example.classdiagramlib.bean;

import java.util.ArrayList;
import java.util.List;

public class UMLClass {
    private String name;
    private String fullName;
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

    public List<UMLLink> getLinks() {
        return links;
    }

    public List<UMLNote> getNotes() {
        return notes;
    }

}
