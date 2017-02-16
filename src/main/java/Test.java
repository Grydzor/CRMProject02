import entity.*;
import enum_types.OrderStatus;
import service.*;
import util.HibernateSessionFactory;

import java.util.Date;

public class Test {
    public static void main(String[] args) {
        ProductService productService = new ProductServiceImpl();
        OrderService orderService = new OrderServiceImpl();
        ItemService itemService = new ItemServiceImpl();

        /*
        Employee employee = new Employee("Stef", "Jbs", 20, Sex.MALE, Item.ADMIN);
        User user = new User("man", "man", employee);

        service.add(employee);
        service.add(user);

        Employee employeeFrom = service.read(Employee.class, 1L);
        User userFrom = service.read(User.class, 1L);

        System.out.println(employeeFrom.getName() + ", " + employeeFrom.getSurname() + ", " + employeeFrom.getAge());
        System.out.println(userFrom.getLogin() + ", " + userFrom.getPassword() + ", " + userFrom.getEmployee().getName());
        */

        Product notebook = new Product("Apple", 1499);
        Order order = new Order("Ivan", "Taras", new Date(2016, 6, 12), OrderStatus.OPEN, 1499);
        Item item = new Item(notebook, 2999, 2, order);

        productService.add(notebook);
        orderService.add(order);
        itemService.add(item);

        /*List<Order> orders = service.findAll(Order.class);
        for (Order order : orders) {
            List<entity.Item> positions = (List<entity.Item>) order.getItems();
            if (!positions.isEmpty()) {
                for (entity.Item item : positions) {
                    System.out.println(item);
                }
            }
        }*/
        HibernateSessionFactory.getSessionFactory().close();
    }
}
