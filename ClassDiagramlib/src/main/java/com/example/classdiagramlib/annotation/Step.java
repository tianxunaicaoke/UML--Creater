package com.example.classdiagramlib.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.CLASS)
@Repeatable(Steps.class)
public @interface Step {
    String value();
    String group() default "";
    String divider() default "";
    String note() default "";
}