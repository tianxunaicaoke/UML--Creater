package com.example.classdiagramlib.fileCreater;

import com.example.classdiagramlib.bean.UMLInvoke;
import com.example.classdiagramlib.bean.UMLLink;
import com.example.classdiagramlib.util.PlantUtil;

import java.io.IOException;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.List;

import javax.annotation.processing.Filer;
import javax.tools.FileObject;
import javax.tools.StandardLocation;

import static com.example.classdiagramlib.fileCreater.Template.END;
import static com.example.classdiagramlib.fileCreater.Template.LINE_FEED;
import static com.example.classdiagramlib.fileCreater.Template.START;

public class SequenceFileGenerator implements UMLFileGenerator<UMLInvoke> {
    private List<UMLInvoke> invokes;
    private URI plantUMLURI;

    @Override
    public void setNode(List<UMLInvoke> list) {
      invokes = list;
    }

    @Override
    public void writeFile(Filer filer) {
        Writer writer = null;
        try {
            FileObject filerSourceFile = filer.createResource(StandardLocation.CLASS_OUTPUT, "UML", "uml2.puml");
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

    private String convert() {
        StringBuilder stringBuilder = new StringBuilder();
        UMLInvoke start = invokes.get(0);
        stringBuilder.append(START)
                .append(LINE_FEED);
        while (start != null) {
            stringBuilder.append(start.toUMLString())
                    .append(LINE_FEED);
            start = start.getNext();
        }
        stringBuilder.append(END);
        return stringBuilder.toString();
    }

    @Override
    public void createPNG() {
        if (plantUMLURI == null) {
            return;
        }
        try {
            PlantUtil.createPng(plantUMLURI.toURL().toString().substring(6));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
