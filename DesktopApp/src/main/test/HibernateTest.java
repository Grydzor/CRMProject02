import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import util.ApplicationContextFactory;

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
}
