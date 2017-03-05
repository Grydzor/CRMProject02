package TestService;

import dao.ProductDAO;
import entity.Product;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import service.ProductService;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class ProductServiceTest {
    private ApplicationContext context;
    private ProductService productService;

    @Before
    public void init(){
        context = new ClassPathXmlApplicationContext(
                "/spring/spring-config.xml");
        productService = context.getBean("productService", ProductService.class);
    }

    @Test
    public void testRead(){
        List<Product> productList = productService.findAll();
        Product product = productService.read(productList.get(0).getId());

        assertNotNull("Проверка чтения первого продукта", product);
    }


    @Test
    public void createUpdateDelete(){
        Product product = context.getBean(Product.class);
        product.setName("Meat");
        product.setPrice(new BigDecimal("89.67"));

        Long id = productService.create(product);
        Product productReturned = productService.read(id);

        assertEquals("Проверка корректности записаного продукта",
                productReturned.toString(),
                product.toString());

        Product product1 = context.getBean(Product.class);
        product1.setName("Meat");
        product1.setPrice(new BigDecimal("88.66"));
        product1.setId(id);


        productService.update(product1);
        Product productReturned1 = productService.read(id);

        assertEquals("Проверка корректности обновления информации о продукте",
                productReturned1.toString(),
                product1.toString());

        productService.delete(productReturned1);
        Product productReturned2 = productService.read(id);

        assertNull("Проверка корректности удаления информации о продукте", productReturned2);
    }
}
