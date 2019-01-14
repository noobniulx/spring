package com.niulx.spring.beans;

/**
 * @Date 2019-01-14 下午 6:06
 * @Created by nlx
 */
public class BeanWrapper {

    private Object wrapperInstance;

    //原始的通过反射new出来，要把包装起来，存下来
    private Object originalInstance;

    private BeanPostProcessor postProcessor;


    public BeanWrapper(Object instance) {
        this.wrapperInstance = instance;
        this.originalInstance = instance;
    }


    // 返回代理以后的Class
    // 可能会是这个 $Proxy0
    public Class<?> getWrappedClass() {
        return this.wrapperInstance.getClass();
    }


    public Object getWrappedInstance() {
        return this.wrapperInstance;
    }


    public Object getOriginalInstance() {
        return this.originalInstance = originalInstance;
    }


    public BeanPostProcessor getPostProcessor() {
        return postProcessor;
    }

    public void setPostProcessor(BeanPostProcessor postProcessor) {
        this.postProcessor = postProcessor;
    }


}
