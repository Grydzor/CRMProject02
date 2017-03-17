package web.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContextFactory {
    @Autowired
    private static ApplicationContext applicationContext;

    private ApplicationContextFactory() {}

    public static ApplicationContext getApplicationContext(){
        return applicationContext;
    }
}
