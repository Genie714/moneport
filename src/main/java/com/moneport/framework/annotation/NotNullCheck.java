package com.moneport.framework.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NotNullCheck {
    String message() default "조회된 결과가 없습니다.";
    Class<? extends RuntimeException> exception() default RuntimeException.class;
}
