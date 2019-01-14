package com.niulx.spring.contexts;

import com.niulx.spring.annotation.Autowired;
import com.niulx.spring.annotation.Controller;
import com.niulx.spring.annotation.Service;
import com.niulx.spring.beans.BeanDefinition;
import com.niulx.spring.beans.BeanPostProcessor;
import com.niulx.spring.beans.BeanWrapper;
import com.niulx.spring.contexts.support.BeanDefinitionReader;
import com.niulx.spring.core.BeanFactory;
import com.niulx.spring.test.MyAction;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Date 2019-01-14 下午 4:32
 * @Created by nlx
 */
public class ApplicationContext implements BeanFactory {


    private String[] configLocations;

    private BeanDefinitionReader reader;

    /* ioc容器 */
    private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, BeanDefinition>();

    /* 单例容器 */
    private Map<String, Object> beanCacheMap = new HashMap<String, Object>();


    /* 用来存储所有的被代理过的对象 */
    private Map<String, BeanWrapper> beanWrapperMap = new ConcurrentHashMap<String, BeanWrapper>();


    public ApplicationContext(String... locations) {
        this.configLocations = locations;
        refresh();
    }


    private void refresh() {

        // 定位
        reader = new BeanDefinitionReader(configLocations);

        // 加载
        List<String> beanDefinitions = reader.loadBeanDefinitions();

        // 注册
        doRegister(beanDefinitions);

        // 依赖注入 lazy_init = false
        doAutowired();


        MyAction myAction = (MyAction) getBean("myAction");

        myAction.query();

    }

    // 自动化的依赖注入
    private void doAutowired() {
        for (Map.Entry<String, BeanDefinition> beanDefinitionEntry : beanDefinitionMap.entrySet()) {
            String beanName = beanDefinitionEntry.getKey();
            if (!beanDefinitionEntry.getValue().isLazyInit()) {
                getBean(beanName);
            }
        }

//        for (Map.Entry<String, BeanWrapper> beanDefinitionEntry : beanWrapperMap.entrySet()) {
//            populateBean(beanDefinitionEntry.getKey(), beanDefinitionEntry.getValue().getOriginalInstance());
//        }
    }


    private void populateBean(String beanName, Object instance) {
        Class clazz = instance.getClass();

        if (!(clazz.isAnnotationPresent(Controller.class) || clazz.isAnnotationPresent(Service.class))) {
            return;
        }

        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {

            if (!field.isAnnotationPresent(Autowired.class)) {
                continue;
            }

            Autowired annotation = field.getAnnotation(Autowired.class);

            String name = annotation.value().trim();

            if ("".equals(name)) {
                name = field.getType().getName();
            }

            field.setAccessible(true);

            try {
                field.set(instance, beanWrapperMap.get(name).getWrappedInstance());
            } catch (Exception e) {
                e.getStackTrace();
            }
        }

    }

    /**
     * 将beanDefinition注册到ioc容器中
     *
     * @param beanDefinitions
     */
    private void doRegister(List<String> beanDefinitions) {

        try {
            for (String className : beanDefinitions) {
                Class<?> clazz = Class.forName(className);
                if (clazz.isInterface()) {
                    continue;
                }
                BeanDefinition definition = reader.registerBean(className);
                if (definition != null) {
                    beanDefinitionMap.put(definition.getFactoryBeanName(), definition);
                }

                Class<?>[] clazzInterfaces = clazz.getInterfaces();
                for (Class<?> i : clazzInterfaces) {
                    beanDefinitionMap.put(i.getName(), definition);
                }
                // 容器初始化完毕

            }
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    /**
     * 依赖注入
     *
     * @param beanName
     * @return
     */
    @Override
    public Object getBean(String beanName) {

        BeanDefinition definition = beanDefinitionMap.get(beanName);
        String beanClassName = definition.getBeanClassName();
        try {
            // 生成通知事件
            BeanPostProcessor processor = new BeanPostProcessor();

            Object instanceBean = initInstanceBean(definition);

            if (instanceBean == null) return null;

            //实例初始化之前调用一次
            processor.postProcessBeforeInitialization(instanceBean, beanName);

            BeanWrapper wrapper = new BeanWrapper(instanceBean);

            beanWrapperMap.put(beanName, wrapper);

            // 实例初始化之后调用一次
            processor.postProcessAfterInitialization(instanceBean, beanName);

            populateBean(beanName, instanceBean);

            return beanWrapperMap.get(beanName).getWrappedInstance();

        } catch (Exception e) {
            e.getStackTrace();
            return null;
        }

    }

    //生成bean实例
    private Object initInstanceBean(BeanDefinition definition) {
        Object instance = null;

        String className = definition.getBeanClassName();

        try {
            if (beanCacheMap.containsKey(className)) {
                instance = beanCacheMap.get(className);
            } else {
                synchronized (beanCacheMap) {
                    Class<?> clazz = Class.forName(className);
                    instance = clazz.newInstance();
                    beanCacheMap.put(className, instance);
                }
            }
            return instance;
        } catch (Exception e) {
            e.getStackTrace();
        }
        return null;
    }
}
