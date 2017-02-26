package TestDAO;


import dao.ProductDAO;
import dao.StorageDAO;
import entity.Storage;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.Assert.assertNotNull;

public class StorageDAOTest {
    private ApplicationContext context;
    private ProductDAO productDAO;
    private StorageDAO storageDAO;

    @Before
    public void init(){
        context = new ClassPathXmlApplicationContext(
                "/spring/spring-config.xml");
        productDAO = context.getBean("productDAO", ProductDAO.class);
        storageDAO = context.getBean("storageDAO", StorageDAO.class);
    }

    @Test
    public void testRead(){
        Storage storage = storageDAO.read(1L);
        System.out.println(storage.toString());

        assertNotNull("Проверка чтения первого работника", storage);
    }


//    @Test
//    public void CheckEmployeeDAO(){
//        Product product = productDAO.read(2L);
//
//        Storage storage = context.getBean(Storage.class);
//        storage.setAmount(20);
//        storage.setProduct(product);
//
//        storageDAO.create(storage);
//        Storage storageReturned = storageDAO.read(2L);
//
//        assertEquals("Проверка корректности записаного сотрудника",
//                storageReturned.toString(),
//                storage.toString());
//
//        Storage storage1 = context.getBean(Storage.class);
//        storage1.setAmount(15);
//        storage1.setProduct(product);
//
//
//        storageDAO.update(storage1);
//        Storage storageReturned1 = storageDAO.read(2L);
//
//        assertEquals("Проверка корректности обновления информации о сотруднике",
//                storageReturned1.toString(),
//                storage1.toString());
//
//        storageDAO.delete(storageReturned1);
//        Storage storageReturned2 = storageDAO.read(2L);
//
//        assertNull("Проверка корректности удаления информации о сотруднике", storageReturned2);
//    }
}
