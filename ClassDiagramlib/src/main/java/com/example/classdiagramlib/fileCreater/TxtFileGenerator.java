package com.example.classdiagramlib.fileCreater;

import com.example.classdiagramlib.bean.UMLClass;
import com.example.classdiagramlib.bean.UMLLink;
import com.example.classdiagramlib.bean.UMLNode;
import com.example.classdiagramlib.bean.UMLNote;
import com.example.classdiagramlib.bean.UMLPackage;
import com.example.classdiagramlib.classLoader.DynamicClassPathLoader;

import net.sourceforge.plantuml.Run;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.io.IOException;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.processing.Filer;
import javax.tools.FileObject;
import javax.tools.StandardLocation;

import static com.example.classdiagramlib.fileCreater.Template.*;


public class TxtFileGenerator implements UMLFileGenerator {

    private List<UMLNode> list = new ArrayList<>();
    private List<UMLLink> links = new ArrayList<>();
    private URI plantUMLURI;

    @Override
    public void setNode(List<UMLNode> list) {
        this.list = list;
    }

    @Override
    public void writeFile(Filer filer) {
        Writer writer = null;
        try {
            FileObject filerSourceFile = filer.createResource(StandardLocation.CLASS_OUTPUT, "UML", "uml.puml");
            plantUMLURI = filerSourceFile.toUri();
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

    @Override
    public void createPNG() {
        if (plantUMLURI == null) {
            return;
        }
        String[] classPath = {"F:\\Create-UML\\UML--Creater\\ClassDiagramlib\\libs\\plantuml.jar"};
        DynamicClassPathLoader classPathLoader = new DynamicClassPathLoader(classPath);
        classPathLoader.loadClass();
        try {
            String[] args = {plantUMLURI.toURL().toString().substring(6)};
            Run.main(args);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
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
        if (!links.isEmpty()) {
            for (UMLLink link : links) {
                stringBuilder.append(link.toUMLString())
                        .append(LINE_FEED);
            }
        }
        return stringBuilder.toString();
    }

    private String convertNodeToString(UMLNode node) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(nodeTemplate(node.getName()))
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
        stringBuilder.append(SPACE)
                .append(packageTemplate(umlPackage.getName()))
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
        stringBuilder.append(SPACE + SPACE);
        List<UMLLink> umlLinks = umlClass.getLinks();
        if (umlClass.getElement().getKind().isClass()) {
            stringBuilder.append(classSuperTemplate(umlClass.getName(), null, null));
        } else if (umlClass.getElement().getKind().isInterface()) {
            stringBuilder.append(interfaceSuperTemplate(umlClass.getName(), null));
        }
        stringBuilder.append(LINE_FEED);
        if (!umlLinks.isEmpty()) {
            links.addAll(umlLinks);
        }
        if (!umlClass.getNotes().isEmpty()) {
            for (UMLNote note : umlClass.getNotes()) {
                stringBuilder.append(SPACE + SPACE)
                        .append(noteTemplate(umlClass.getName(), note.getNote()))
                        .append(LINE_FEED);
            }
        }
        return stringBuilder.toString();
    }
}
