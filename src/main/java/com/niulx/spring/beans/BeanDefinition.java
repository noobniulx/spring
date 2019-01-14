package com.niulx.spring.beans;

/**
 * @Date 2019-01-14 下午 5:09
 * @Created by nlx
 */

/**
 * 保存配置文件中的信息
 */
public class BeanDefinition {

    private String beanClassName;

    private String factoryBeanName;

    private boolean lazyInit = false;

    public String getBeanClassName() {
        return beanClassName;
    }

    public void setBeanClassName(String beanClassName) {
        this.beanClassName = beanClassName;
    }

    public String getFactoryBeanName() {
        return factoryBeanName;
    }

    public void setFactoryBeanName(String factoryBeanName) {
        this.factoryBeanName = factoryBeanName;
    }

    public boolean isLazyInit() {
        return lazyInit;
    }

    public void setLazyInit(boolean lazyInit) {
        this.lazyInit = lazyInit;
    }
}
