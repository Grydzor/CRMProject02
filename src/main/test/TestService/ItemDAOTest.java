package TestService;


import dao.ItemDAO;
import dao.OrderDAO;
import dao.ProductDAO;
import entity.Item;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.Assert.assertNotNull;

public class ItemDAOTest {
    private ApplicationContext context;
    private ProductDAO productDAO;
    private ItemDAO itemDAO;
    private OrderDAO orderDAO;

    @Before
    public void init(){
        context = new ClassPathXmlApplicationContext(
                "/spring/spring-config.xml");
        productDAO = context.getBean("productDAO", ProductDAO.class);
        itemDAO = context.getBean("itemDAO", ItemDAO.class);
        orderDAO = context.getBean("orderDAO", OrderDAO.class);
    }

    @Test
    public void testRead(){
        Item item = itemDAO.read(1L);
        System.out.println(item.toString());

        assertNotNull("Проверка чтения первого работника", item);
    }

//    @Test
//    public void CheckItemDAO(){
//        Product product = productDAO.read(1L);
//        Order order = orderDAO.read(1L);
//
//        Item item = context.getBean(Item.class);
//        item.setAmount(20);
//        item.setProduct(product);
//        item.setOrder(order);
//
//        Long id = itemDAO.create(item);
//        Item itemReturned = itemDAO.read(id);
//
//        assertEquals("Проверка корректности записаного сотрудника",
//                itemReturned.toString(),
//                item.toString());
//
//        Item item1 = context.getBean(Item.class);
//        item1.setAmount(15);
//        item1.setProduct(product);
//        item1.setOrder(order);
//        item1.setId(id);
//
//
//        itemDAO.update(item1);
//        Item itemReturned1 = itemDAO.read(id);
//
//        assertEquals("Проверка корректности обновления информации о сотруднике",
//                itemReturned1.toString(),
//                item1.toString());
//
//        itemDAO.delete(itemReturned1);
//        Item itemReturned2 = itemDAO.read(id);
//
//        assertNull("Проверка корректности удаления информации о сотруднике", itemReturned2);
//    }
}
