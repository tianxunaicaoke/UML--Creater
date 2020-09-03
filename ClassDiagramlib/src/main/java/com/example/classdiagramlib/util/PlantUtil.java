package com.example.classdiagramlib.util;


import net.sourceforge.plantuml.ErrorStatus;
import net.sourceforge.plantuml.Option;
import net.sourceforge.plantuml.Run;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class PlantUtil {
    public static void createPng(String path){
        try {
            String[] args = {path};
            Option option = new Option(args);
            ErrorStatus error = ErrorStatus.init();
            Method method = Run.class.getDeclaredMethod("manageAllFiles", Option.class, ErrorStatus.class);
            method.setAccessible(true);
            method.invoke(Run.class.newInstance(), option, error);
        } catch (IOException e) {
            e.printStackTrace();
        }  catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
