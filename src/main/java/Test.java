import entity.*;
import enum_types.OrderStatus;
import service.*;
import util.HibernateSessionFactory;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.GregorianCalendar;

public class Test {
    public static void main(String[] args) {
        ProductService productService = ProductServiceImpl.getInstance();
        OrderService orderService = OrderServiceImpl.getInstance();
        ItemService itemService = ItemServiceImpl.getInstance();
        CustomerService customerService = CustomerServiceImpl.getInstance();
        EmployeeService employeeService = EmployeeServiceImpl.getInstance();

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
        Product product1 = new Product("Apple", BigDecimal.valueOf(49.99));
        Product product2 = new Product("Banana", BigDecimal.valueOf(149.99));
        Product product3 = new Product("Cherry", BigDecimal.valueOf(229.99));
        Product product4 = new Product("Orange", BigDecimal.valueOf(179.99));
        Product product5 = new Product("Grapes", BigDecimal.valueOf(319.99));

        productService.add(product1);
        productService.add(product2);
        productService.add(product3);
        productService.add(product4);
        productService.add(product5);

        Employee manager = employeeService.read(36L);
        Customer customer = new Customer("Customer", "Customerevich");
        Order order = new Order(manager, customer, new Date(new GregorianCalendar(2016,5,23).getTimeInMillis()), OrderStatus.OPENED, new BigDecimal(1499));
        Order order2 = new Order(manager, customer, new Date(new GregorianCalendar(2016,5,23).getTimeInMillis()), OrderStatus.OPENED, new BigDecimal(1499));
        Item item = new Item(productService.read(1L), 2, order);
        Item item2 = new Item(productService.read(4L), 6, order);
        Item item3 = new Item(productService.read(3L), 5, order);
        Item item4 = new Item(productService.read(4L), 12, order2);
        Item item5 = new Item(productService.read(2L), 6, order2);
        Item item6 = new Item(productService.read(5L), 17, order2);

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
