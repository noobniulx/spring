package com.niulx.spring.core;

/**
 * @Date 2019-01-14 下午 4:34
 * @Created by nlx
 */
public interface BeanFactory {

    /**
     * 从IOC容器中获取一个bean实例
     * @param beanName
     * @return
     */
    Object getBean(String beanName);
}
