package util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ApplicationContextFactory {
    private static ApplicationContext applicationContext = build();

    private ApplicationContextFactory() {}

    private static ApplicationContext build(){
        if(applicationContext == null){
            applicationContext = new ClassPathXmlApplicationContext(
                    "/spring/spring-config.xml");
        }
        return applicationContext;
    }

    public static ApplicationContext getApplicationContext(){
        return applicationContext;
    }
}
