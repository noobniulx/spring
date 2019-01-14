package com.niulx.spring.beans;

/**
 * @Date 2019-01-14 下午 5:56
 * @Created by nlx
 */
public class BeanPostProcessor {

    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        return bean;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) {
        return bean;
    }


}
