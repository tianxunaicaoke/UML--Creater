package com.example.classdiagramlib.processer;

import com.example.classdiagramlib.bean.UMLClass;
import com.example.classdiagramlib.bean.UMLComposition;
import com.example.classdiagramlib.bean.UMLExtendsForClass;
import com.example.classdiagramlib.bean.UMLNode;
import com.example.classdiagramlib.classLoader.DynamicClassPathLoader;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;


public class ClassUMLByClassLoaderProcessor extends ClassUMLProcessor {
    @Override
    public void buildGraph(List<UMLNode> nodes) {
        String[] classPath = {"F:\\MAnnotation-master\\UMLCreate\\app\\temmp\\"};
        DynamicClassPathLoader classPathLoader = new DynamicClassPathLoader(classPath);
        classPathLoader.loadClass();
        if (exitClass != null && !exitClass.entrySet().isEmpty()) {
            Iterator<UMLClass> iterator = exitClass.values().iterator();
            while (iterator.hasNext()) {
                UMLClass umlClass = iterator.next();
                try {
                    UMLExtendsForClass umlExtend = null;
                    Class c = this.getClass().getClassLoader().loadClass(umlClass.getFullName());
                    Class superClass = c.getSuperclass();
                    if (!superClass.getSimpleName().equals("Object")) {
                        umlExtend = new UMLExtendsForClass();
                        umlExtend.setOrigin(umlClass.getName());
                        umlExtend.setTarget(superClass.getSimpleName());
                    }
                    Class<?>[] superInterfaces = c.getInterfaces();
                    for (Class superI : superInterfaces) {
                        if (umlExtend == null) {
                            umlExtend = new UMLExtendsForClass();
                            umlExtend.setOrigin(umlClass.getName());
                        }
                        umlExtend.addTarget(superI.getSimpleName());
                    }
                    if (umlExtend != null) {
                        umlClass.addLink(umlExtend);
                    }
                    Field[] holdClasses = c.getFields();
                    for (Field item : holdClasses) {
                        if (exitClass.containsKey(item.getType().getCanonicalName())) {
                            UMLComposition composition = new UMLComposition();
                            composition.setOrigin(umlClass.getName());
                            composition.setTarget(item.getType().getSimpleName());
                            umlClass.addLink(composition);
                        }
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
