package TestService;


import dao.OrderDAO;
import entity.Order;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.Assert.assertNotNull;

public class OrderDAOTest {
    private ApplicationContext context;
    private OrderDAO orderDAO;

    @Before
    public void init(){
        context = new ClassPathXmlApplicationContext(
                "/spring/spring-config.xml");
        orderDAO = context.getBean("orderDAO", OrderDAO.class);
    }

    @Test
    public void testRead(){
        Order order = orderDAO.read(1L);
        System.out.println(order.toString());

        assertNotNull("Проверка чтения первого работника", order);
    }


//    @Test
//    public void CheckEmployeeDAO(){
//        Order order = context.getBean(Order.class);
//        order.setStatus(OrderStatus.OPENED);
//
//        Long id = orderDAO.create(order);
//        Order orderReturned = orderDAO.read(id);
//
//        assertEquals("Проверка корректности записаного сотрудника",
//                orderReturned.toString(),
//                order.toString());
//
//        Order order1 = context.getBean(Order.class);
//        order1.setStatus(OrderStatus.CANCELED);
//        order1.setId(id);
//
//
//        orderDAO.update(order1);
//        Order orderReturned1 = orderDAO.read(id);
//
//        assertEquals("Проверка корректности обновления информации о сотруднике",
//                orderReturned1.toString(),
//                order1.toString());
//
//        orderDAO.delete(orderReturned1);
//        Order orderReturned2 = orderDAO.read(id);
//
//        assertNull("Проверка корректности удаления информации о сотруднике", orderReturned2);
//    }
}
