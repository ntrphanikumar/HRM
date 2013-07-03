package com.nrkpj.commetial.hrm.utils;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ServiceFactory {

    private static ApplicationContext context;

    private ServiceFactory() {
    }

    public static void initializeSpringContext() {
        context = new ClassPathXmlApplicationContext("spring/applicationContext-*.xml");
    }

    public static <T> T getService(Class<T> clazz) {
        return context.getBean(clazz);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getService(String name) {
        return (T) context.getBean(name);
    }
}
