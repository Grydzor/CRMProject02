package TestService;

import dao.ProductDAO;
import entity.Product;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class ProductDAOTest {
    private ApplicationContext context;
    private ProductDAO productDAO;

    @Before
    public void init(){
        context = new ClassPathXmlApplicationContext(
                "/spring/spring-config.xml");
        productDAO = context.getBean("productDAO", ProductDAO.class);
    }

    @Test
    public void testRead(){
        Product product = productDAO.read(1L);
        System.out.println(product.toString());

        assertNotNull("Проверка чтения первого работника", product);
    }


    @Test
    public void CheckEmployeeDAO(){
        Product product = context.getBean(Product.class);
        product.setName("Meat");
        product.setPrice(new BigDecimal("89"));

        Long id = productDAO.create(product);
        Product productReturned = productDAO.read(id);

        assertEquals("Проверка корректности записаного сотрудника",
                productReturned.toString(),
                product.toString());

        Product product1 = context.getBean(Product.class);
        product1.setName("Meat");
        product1.setPrice(new BigDecimal("88"));
        product1.setId(id);


        productDAO.update(product1);
        Product productReturned1 = productDAO.read(id);

        assertEquals("Проверка корректности обновления информации о сотруднике",
                productReturned1.toString(),
                product1.toString());

        productDAO.delete(productReturned1);
        Product productReturned2 = productDAO.read(id);

        assertNull("Проверка корректности удаления информации о сотруднике", productReturned2);
    }
}
