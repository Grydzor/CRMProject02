package TestService;

import dao.CustomerDAO;
import entity.Customer;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import service.CustomerService;

import java.util.List;

import static org.junit.Assert.*;


public class CustomerServiceTest {
    private ApplicationContext context;
    private CustomerService customerService;

    @Before
    public void init(){
        context = new ClassPathXmlApplicationContext(
                "/spring/spring-config.xml");
        customerService = context.getBean("customerService", CustomerService.class);
    }

    @Test
    public void testRead(){
        List<Customer> customerList = customerService.findAll();
        Customer customer = customerService.read(customerList.get(0).getId());

        assertNotNull("Проверка чтения первого клиента", customer);
    }


    @Test
    public void createUpdateDelete(){
        Customer customer = context.getBean(Customer.class);
        customer.setName("Big");
        customer.setSurname("Money");
        customer.setMobile("1723968");
        customer.setEmail("qiwugorh@laiwr.hg");

        Long id = customerService.create(customer);
        Customer customerReturned = customerService.read(id);

        assertEquals("Проверка корректности записаного клиента",
                customerReturned.toString(),
                customer.toString());

        Customer customer1 = context.getBean(Customer.class);
        customer1.setName("Ivan");
        customer1.setSurname("Ivanov");
        customer1.setMobile("2143534");
        customer1.setEmail("olh@uyfi");
        customer1.setId(id);

        customerService.update(customer1);
        Customer customerReturned1 = customerService.read(id);

        assertEquals("Проверка корректности обновления информации о клиенте",
                customerReturned1.toString(),
                customer1.toString());

        customerService.delete(customerReturned1);
        Customer customerReturned2 = customerService.read(id);

        assertNull("Проверка корректности удаления информации о клиенте", customerReturned2);
    }
}
