import entity.*;
import enum_types.OrderStatus;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import service.*;
import util.HibernateSessionFactory;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class Test {
    public static void main(String[] args) {
        ProductService productService = new ProductServiceImpl();
        OrderService orderService = new OrderServiceImpl();
        ItemService itemService = new ItemServiceImpl();
        CustomerService customerService = new CustomerServiceImpl();


        /*Product notebook = new Product("Apple", 1499);
        Customer customer = new Customer("Customer", "Customerevich");
        Order order = new Order("Ivan", customer, Date.from(LocalDate.of(2016, 6, 12).atStartOfDay(ZoneId.systemDefault()).toInstant()), OrderStatus.OPEN, 1499);
        Item item = new Item(notebook, 2999, 2, order);

        productService.add(notebook);
        customerService.add(customer);
        orderService.add(order);
        itemService.add(item);*/


        Order order = orderService.read(1L);
        System.out.println(order.getCustomer());
        /*Customer customer = customerService.read(1L);

        for (Order order1 : customer.getOrders()) {
            System.out.println(order1);
        }*/

        Collection<Item> items;
        Hibernate.initialize(items = order.getItems());

        for (Item item : items) {
            System.out.println(item);
        }

        HibernateSessionFactory.getSessionFactory().close();
    }
}
