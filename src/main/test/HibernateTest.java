import org.hibernate.SessionFactory;
import org.junit.Test;
import util.HibernateSessionFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class HibernateTest {

    @Test
    public void HibernateSessionFactoryTest(){
        SessionFactory sessionFactory = HibernateSessionFactory.getSessionFactory();
        if(sessionFactory.isClosed()){fail("Hibernate Session Factory not started!");}
        sessionFactory.close();
        if(!sessionFactory.isClosed()){fail("Hibernate Session Factory not closed!");}
    }

    @Test
    public void HibernateObjectTest(){
        Class hibernate = HibernateSessionFactory.class;
        Constructor[] constructor = hibernate.getDeclaredConstructors();
        assertEquals("HibernateSessionFactory have more then 1 constructor", 1,constructor.length);

        int modifier = constructor[0].getModifiers();
        if(!Modifier.isPrivate(modifier)) {fail("Constructor HibernateSessionFactory is not private!");}

    }
}
