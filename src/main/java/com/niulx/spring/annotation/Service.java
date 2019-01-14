package com.niulx.spring.annotation;

import java.lang.annotation.*;

/**
 * @Date 2019-01-14 下午 6:20
 * @Created by nlx
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Service {

    String value() default "";
}
