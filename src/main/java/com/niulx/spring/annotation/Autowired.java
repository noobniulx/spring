package com.niulx.spring.annotation;

import java.lang.annotation.*;

/**
 * @Date 2019-01-14 下午 6:21
 * @Created by nlx
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Autowired {

    String value() default "";
}
