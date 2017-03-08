package TestService;

import entity.Customer;
import entity.Employee;
import entity.Order;
import enum_types.OrderStatus;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import service.CustomerService;
import service.EmployeeService;
import service.OrderService;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class OrderServiceTest {
    private ApplicationContext context;
    private OrderService orderService;
    private EmployeeService employeeService;
    private CustomerService customerService;

    @Before
    public void init(){
        context = new ClassPathXmlApplicationContext(
                "/spring/spring-config.xml");
        orderService = context.getBean("orderService", OrderService.class);
        employeeService = context.getBean("employeeService", EmployeeService.class);
        customerService = context.getBean("customerService", CustomerService.class);
    }

    @Test
    public void testRead(){
        List<Order> orderList = orderService.findAll();
        Order order = orderService.read(orderList.get(0).getId());

        assertNotNull("Проверка чтения первого заказа", order);
    }

    @Test
    public void createUpdateDelete(){
        Customer customer = customerService.findAll().get(0);
        Employee employee = employeeService.findAll().get(0);
        Order order = context.getBean(Order.class);
        order.setStatus(OrderStatus.OPENED);
        order.setCustomer(customer);
        order.setManager(employee);
        order.setDate(Date.valueOf(LocalDate.now()));
        order.setDeadline(Date.valueOf(LocalDate.now()));

        Long id = orderService.create(order);
        Order orderReturned = orderService.read(id);

        assertEquals("Проверка корректности записаного заказа",
                orderReturned.toString(),
                order.toString());

        Order order1 = context.getBean(Order.class);
        order1.setStatus(OrderStatus.CANCELED);
        order1.setId(id);
        order1.setCustomer(customer);
        order1.setManager(employee);
        order1.setDate(Date.valueOf(LocalDate.now()));
        order1.setDeadline(Date.valueOf(LocalDate.now()));


        orderService.update(order1);
        Order orderReturned1 = orderService.read(id);

        assertEquals("Проверка корректности обновления информации о заказе",
                orderReturned1.toString(),
                order1.toString());

        orderService.delete(orderReturned1);
        Order orderReturned2 = orderService.read(id);

        assertNull("Проверка корректности удаления информации о заказе", orderReturned2);
    }
}
