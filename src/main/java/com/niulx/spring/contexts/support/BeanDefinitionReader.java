package com.niulx.spring.contexts.support;

import com.niulx.spring.beans.BeanDefinition;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @Date 2019-01-14 下午 4:42
 * @Created by nlx
 */
// 读取配置文件
public class BeanDefinitionReader {

    private Properties config = new Properties();

    //在配置文件中，用来获取自动扫描的包名的key
    private final String SCAN_PACKAGE = "scanPackage";

    private List<String> registyBeanClasses = new ArrayList<String>();

    public BeanDefinitionReader(String... locations) {

        InputStream inputStream = null;

        for (int i = 0; i < locations.length; i++) {
            inputStream = this.getClass().getClassLoader().getResourceAsStream(locations[i].replace("classpath:", ""));
            try {
                config.load(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        doScan(config.getProperty(SCAN_PACKAGE));
    }

    public List<String> loadBeanDefinitions() {
        return this.registyBeanClasses;
    }

    public Properties getConfig() {
        return this.config;
    }


    // 扫描所有相关联的类
    private void doScan(String pkg) {
        URL url = this.getClass().getClassLoader().getResource("/" + pkg.replaceAll("\\.","/"));

        File files = new File(url.getFile());

        for (File file : files.listFiles()) {
            if (file.isDirectory()) {
                doScan(pkg + "." + file.getName());
            } else {
                registyBeanClasses.add(pkg + "." + file.getName().replace(".class", ""));
            }
        }

    }

    /**
     * 每注册一个className，就返回一个beanDefinition
     */
    public BeanDefinition registerBean(String className) {
        BeanDefinition beanDefinition = new BeanDefinition();
        beanDefinition.setBeanClassName(className);
        beanDefinition.setFactoryBeanName(lowerFirstCase(className.substring(className.lastIndexOf(".") + 1)));
        return beanDefinition;
    }


    private String lowerFirstCase(String str) {
        char[] chars = str.toCharArray();
        chars[0] += 32;
        return String.valueOf(chars);
    }


}
