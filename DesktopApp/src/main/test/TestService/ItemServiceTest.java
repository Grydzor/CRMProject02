package TestService;

import entity.Item;
import entity.Order;
import entity.Product;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import service.ItemService;
import service.OrderService;
import service.ProductService;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class ItemServiceTest {
    private ApplicationContext context;
    private ProductService productService;
    private ItemService itemService;
    private OrderService orderService;

    @Before
    public void init(){
        context = new ClassPathXmlApplicationContext(
                "/spring/spring-config.xml");
        productService = context.getBean("productService", ProductService.class);
        itemService = context.getBean("itemService", ItemService.class);
        orderService = context.getBean("orderService", OrderService.class);
    }

    @Test
    public void testRead(){
        List<Item> itemList = itemService.findAll();
        Item item = itemService.read(itemList.get(0).getId());
        System.out.println(item.toString());

        assertNotNull("Проверка чтения первый список продуктов", item);
    }

    @Test
    public void createUpdateDelete(){
        Product product = productService.findAll().get(0);
        Order order = orderService.findAll().get(0);

        Item item = context.getBean(Item.class);
        item.setAmount(20);
        item.setProduct(product);
        item.setOrder(order);
        item.setPrice(new BigDecimal("123.35"));

        Long id = itemService.create(item);
        Item itemReturned = itemService.read(id);

        assertEquals("Проверка корректности записаного списока продуктов",
                itemReturned.toString(),
                item.toString());

        Item item1 = context.getBean(Item.class);
        item1.setAmount(15);
        item1.setProduct(product);
        item1.setOrder(order);
        item1.setPrice(new BigDecimal("123.35"));
        item1.setId(id);


        itemService.update(item1);
        Item itemReturned1 = itemService.read(id);

        assertEquals("Проверка корректности обновления информации о списоке продуктов",
                itemReturned1.toString(),
                item1.toString());

        itemService.delete(itemReturned1);
        Item itemReturned2 = itemService.read(id);

        assertNull("Проверка корректности удаления информации о списоке продуктов", itemReturned2);
    }
}
