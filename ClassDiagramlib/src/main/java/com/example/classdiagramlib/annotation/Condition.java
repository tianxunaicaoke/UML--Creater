package com.example.classdiagramlib.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.CLASS)
public @interface Condition {

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.CLASS)
    @Repeatable(Ifs.class)
    @interface If {
        String value();
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.CLASS)
    @interface Ifs {
        If[] value();
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.CLASS)
    @Repeatable(Thens.class)
    @interface Then{
        String value();
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.CLASS)
    @interface Thens{
        Then[] value();
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.CLASS)
    @interface Elses{
        Else[] value();
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.CLASS)
    @Repeatable(Elses.class)
    @interface Else{
        String value();
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.CLASS)
    @interface ElseIfs{
        ElseIf[] value();
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.CLASS)
    @Repeatable(ElseIfs.class)
    @interface ElseIf{
        String value();
    }
}
