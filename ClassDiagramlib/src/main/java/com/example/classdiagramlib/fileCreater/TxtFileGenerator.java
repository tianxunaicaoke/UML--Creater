package com.example.classdiagramlib.fileCreater;

import com.example.classdiagramlib.bean.UMLClass;
import com.example.classdiagramlib.bean.UMLImplement;
import com.example.classdiagramlib.bean.UMLLink;
import com.example.classdiagramlib.bean.UMLNode;
import com.example.classdiagramlib.bean.UMLPackage;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.processing.Filer;
import javax.tools.FileObject;
import javax.tools.StandardLocation;

import static com.example.classdiagramlib.fileCreater.Template.*;


public class TxtFileGenerator implements UMLFileGenerator {

    List<UMLNode> list;
    List<UMLLink> links = new ArrayList<>();
    @Override
    public void setNode(List<UMLNode> list) {
        this.list = list;
    }

    @Override
    public void writeToFile(Filer filer) {
        Writer writer = null;
        try {
            FileObject filerSourceFile = filer.createResource(StandardLocation.CLASS_OUTPUT,"UML","uml.puml");
            writer = filerSourceFile.openWriter();
            writer.write(convert());
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private String convert() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(START)
                .append(LINE_FEED);
        for (UMLNode node : list) {
            stringBuilder.append(convertNodeToString(node))
                    .append(LINE_FEED);
        }
        stringBuilder.append(convertLink());
        stringBuilder.append(END);
        return stringBuilder.toString();
    }

    private String convertLink() {
        StringBuilder stringBuilder = new StringBuilder();
        if(!links.isEmpty()){
            for (UMLLink link:links){
                stringBuilder.append(link.toUMLString())
                        .append(LINE_FEED);
            }
        }
        return stringBuilder.toString();
    }

    private String convertNodeToString(UMLNode node) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Template.nodeTemplate(node.getName()))
                .append(LINE_FEED);
        List<UMLPackage> packages = node.getPackages();
        if (packages != null && !packages.isEmpty()) {
            for (UMLPackage umlPackage : packages) {
                stringBuilder.append(convertPackageToString(umlPackage))
                        .append(LINE_FEED);
            }
        }
        stringBuilder.append(END_PRACKET)
                .append(LINE_FEED);
        return stringBuilder.toString();
    }

    private String convertPackageToString(@NonNull UMLPackage umlPackage) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Template.packageTemplate(umlPackage.getName()))
                .append(LINE_FEED);
        List<UMLClass> classes = umlPackage.getClasses();
        if (classes != null) {
            for (UMLClass umlClass : classes) {
                stringBuilder.append(convertClassToString(umlClass))
                        .append(LINE_FEED);
            }
        }
        stringBuilder.append(END_PRACKET)
                .append(LINE_FEED);
        return stringBuilder.toString();
    }

    private String convertClassToString(@NonNull UMLClass umlClass) {
        StringBuilder stringBuilder = new StringBuilder();
        List<UMLLink> umlLinks = umlClass.getLinks();
        boolean flag = false;

        if (umlLinks != null && !umlLinks.isEmpty()) {
            for (UMLLink item : umlLinks) {
                if (item instanceof UMLImplement) {
                    stringBuilder.append(item.toUMLString())
                            .append(LINE_FEED);
                    flag = true;
                }else{
                    links.add(item);
                }
            }
        }
        if(!flag){
            stringBuilder.append(Template.classSuperTemplate(umlClass.getName(),null,null));
        }
        return stringBuilder.toString();
    }
}
