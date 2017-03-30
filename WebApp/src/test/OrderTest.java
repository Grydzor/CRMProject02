import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import web.entity.Customer;
import web.entity.Item;
import web.entity.Order;
import web.service.CustomerService;
import web.service.ItemService;
import web.service.OrderService;
import web.service.ProductService;

import java.util.ArrayList;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/context-web.xml")
@WebAppConfiguration
public class OrderTest {
    @Autowired
    private OrderService orderService;
    @Autowired
    private ItemService itemService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private ProductService productService;

    @Test
    public void someTest() {
        Customer customer = new Customer();
        customer.setMobile((int)(Math.random() * 1_000_000) + "");
        customer.setName("name");
        customerService.create(customer);

        Order order = new Order();
        order.setCustomer(customer);
        order.setItems(new ArrayList<>());
        Item item = new Item(productService.read(4L), 2, order);
        Item item2 = new Item(productService.read(2L), 3, order);
        order.getItems().add(item);
        order.getItems().add(item2);
//        for (Item item1 : order.getItems()) {
//            itemService.create(item1);
//        }
        orderService.create(order);
    }

    @Test
    public void someTest2() {
        Order order = orderService.readWithItems(7L);
        Item item = new Item(productService.read(4L), 2, order);
        order.getItems().add(item);
        orderService.update(order);
    }

    @Test
    public void someTest3() {
        Order order = orderService.readWithItems(7L);
        order.getItems().get(0).setAmount(0);
        orderService.update(order);
    }
}
