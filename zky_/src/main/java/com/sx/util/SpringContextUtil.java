package com.sx.util;


import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 在Spring 注解中，普通类获取@Service标记的方法或者bean对象
 */

@Component
public class SpringContextUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext arg0) throws BeansException {
        // TODO Auto-generated method stub
        SpringContextUtil.applicationContext = arg0;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static Object getBean(String name) throws BeansException {
        return applicationContext.getBean(name);
    }

    public static <T> T getBean(Class<T> class1) {
        // TODO Auto-generated method stub
        return (T) applicationContext.getBean(class1);
    }

    public static <T> T getBeanByName(Class<T> clazz) throws BeansException {
        try {
            char[] cs = clazz.getSimpleName().toCharArray();
            cs[0] += 32;
            return (T) applicationContext.getBean(String.valueOf(cs));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static boolean containsBean(String name) {
        return applicationContext.containsBean(name);
    }

    public static boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
        return applicationContext.isSingleton(name);
    }

}
 