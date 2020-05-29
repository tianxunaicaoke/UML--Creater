package com.example.classdiagramlib.classLoader;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

public class DynamicClassPathLoader{
    public String[] paths;
    private Method addURL = initAddMethod();

    private URLClassLoader classloader = (URLClassLoader) this.getClass().getClassLoader();

    private Method initAddMethod() {
        try {
            Method add = this.getClass().getClassLoader().getClass().getDeclaredMethod("addURL", new Class[] { URL.class });
            add.setAccessible(true);
            return add;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public DynamicClassPathLoader(String[] paths) {
        this.paths = paths;
    }

    public void loadClass() {
        for (String s : paths) {
            File file = new File(s);
            loopDirs(file);
        }
    }

    private void loopDirs(File file) {
        if (file.isDirectory()) {
            addURL(file);
            File[] tmps = file.listFiles();
            for (File tmp : tmps) {
                loopDirs(tmp);
            }
        }else if(file.getName().endsWith("jar")){
           addURL(file);
        }
    }

    private void addURL(File file) {
        try {
            addURL.invoke(classloader, file.toURI().toURL());
        }
        catch (Exception e) {
        }
    }
}
