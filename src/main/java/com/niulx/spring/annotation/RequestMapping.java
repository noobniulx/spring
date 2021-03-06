package com.niulx.spring.annotation;

import java.lang.annotation.*;

/**
 * @Date 2019-01-14 下午 6:22
 * @Created by nlx
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestMapping {
    String value() default "";
}
