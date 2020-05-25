package com.example.classdiagramlib.bean;

import java.util.List;

public class UMLNode {
    private String name;
    private List<UMLPackage> packages;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<UMLPackage> getPackages() {
        return packages;
    }

    public void setPackages(List<UMLPackage> packages) {
        this.packages = packages;
    }
}
