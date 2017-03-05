import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import util.ApplicationContextFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class HibernateTest {
    private ApplicationContext context;

    @Autowired
    private SessionFactory sessionFactory;

    @Before
    public void init(){
        context = ApplicationContextFactory.getApplicationContext();
        sessionFactory = context.getBean(SessionFactory.class);
    }

    @Test
    public void HibernateSessionFactoryTest(){
        if(sessionFactory.isClosed()){fail("Hibernate Session Factory not started!");}
    }


//    @Test
//    @After
//    public void HiberSessionClosed(){
//        sessionFactory.close();
//        if(!sessionFactory.isClosed()){fail("Hibernate Session Factory not closed!");}
//    }
}
