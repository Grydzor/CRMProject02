package TestService;

import dao.CustomerDAO;
import entity.Customer;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.Assert.*;


public class CustomerDAOTest {
    private ApplicationContext context;
    private CustomerDAO customerDAO;

    @Before
    public void init(){
        context = new ClassPathXmlApplicationContext(
                "/spring/spring-config.xml");
        customerDAO = context.getBean("customerDAO", CustomerDAO.class);
    }

    @Test
    public void testRead(){
        Customer customer = customerDAO.read(1L);
        System.out.println(customer.toString());

        assertNotNull("Проверка чтения первого работника", customer);
    }


    @Test
    public void CheckEmployeeDAO(){
        Customer customer = context.getBean(Customer.class);
        customer.setName("Big");
        customer.setSurname("Money");
        customer.setMobile("1723968");
        customer.setEmail("qiwugorh@laiwr.hg");

        Long id = customerDAO.create(customer);
        Customer customerReturned = customerDAO.read(id);

        assertEquals("Проверка корректности записаного сотрудника",
                customerReturned.toString(),
                customer.toString());

        Customer customer1 = context.getBean(Customer.class);
        customer1.setName("Ivan");
        customer1.setSurname("Ivanov");
        customer1.setMobile("2143534");
        customer1.setEmail("olh@uyfi");
        customer1.setId(id);


        customerDAO.update(customer1);
        Customer customerReturned1 = customerDAO.read(id);

        assertEquals("Проверка корректности обновления информации о сотруднике",
                customerReturned1.toString(),
                customer1.toString());

        customerDAO.delete(customerReturned1);
        Customer customerReturned2 = customerDAO.read(id);

        assertNull("Проверка корректности удаления информации о сотруднике", customerReturned2);
    }
}
