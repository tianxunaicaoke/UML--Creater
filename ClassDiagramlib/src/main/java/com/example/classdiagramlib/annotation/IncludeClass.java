package com.example.classdiagramlib.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface IncludeClass {
    String umlNode() default "Hello";
    String umlPackage() default "David";
    String umlNote() default "";
}
