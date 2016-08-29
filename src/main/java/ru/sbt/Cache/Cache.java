package ru.sbt.Cache;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Cache {

    CacheType cacheType() default CacheType.IN_MEMORY;

    String fileNamePrefix() default "";

    boolean zip() default true;

    Class[] identityBy() default {};

    int listSize() default 100_000;
}
