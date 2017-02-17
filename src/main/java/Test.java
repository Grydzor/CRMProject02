import entity.*;
import enum_types.OrderStatus;
import enum_types.Position;
import enum_types.Sex;
import javafx.scene.control.TableColumn;
import org.hibernate.Hibernate;
import service.*;
import util.HibernateSessionFactory;

import java.math.BigDecimal;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        ProductService productService = ProductServiceImpl.getInstance();
        OrderService orderService = OrderServiceImpl.getInstance();
        ItemService itemService = ItemServiceImpl.getInstance();
        CustomerService customerService = CustomerServiceImpl.getInstance();

//        Customer customer = customerService.read(1L);
//        List<Order> orders = customer.getOrdersLazy();

        /*for (Order order : customer.getOrders()) {
            System.out.println(order);
        }*/

        /*UserService userService = new UserServiceImpl();
        EmployeeService employeeService = new EmployeeServiceImpl();

        Employee employee = new Employee("Stef", "Jbs", 20, Sex.MALE, Position.MANAGER);
        User user = new User("man", "man", employee);

        employeeService.add(employee);
        userService.add(user);


        Employee employeeFrom = employeeService.read(1L);
        User userFrom = userService.read(1L);

        System.out.println(employeeFrom.getName() + ", " + employeeFrom.getSurname() + ", " + employeeFrom.getAge());
        System.out.println(userFrom.getLogin() + ", " + userFrom.getPassword() + ", " + userFrom.getEmployee().getName());
*/

        Customer customer = new Customer("Customer", "CUstomerevich");
        Product apple = productService.read(1L);
        Order order = new Order("Ivan", customer, new Date(new GregorianCalendar(2016,5,23).getTimeInMillis()), OrderStatus.OPEN, new BigDecimal(1499));
        Order order2 = new Order("Ivan", customer, new Date(new GregorianCalendar(2016,5,23).getTimeInMillis()), OrderStatus.OPEN, new BigDecimal(1499));
        Item item = new Item(apple, 2, order);
        Item item2 = new Item(productService.read(2L), 6, order);
        Item item3 = new Item(productService.read(3L), 5, order);
        Item item4 = new Item(productService.read(4L), 12, order2);
        Item item5 = new Item(productService.read(2L), 6, order2);

        customerService.add(customer);
        orderService.add(order);
        orderService.add(order2);
        itemService.add(item);
        itemService.add(item2);
        itemService.add(item3);
        itemService.add(item4);
        itemService.add(item5);

        /*List<Order> orders = service.findAll(Order.class);
        for (Order order : orders) {
            List<entity.Item> positions = (List<entity.Item>) order.findItems();
            if (!positions.isEmpty()) {
                for (entity.Item item : positions) {
                    System.out.println(item);
                }
            }
        }*/
        HibernateSessionFactory.getSessionFactory().close();
    }
}
