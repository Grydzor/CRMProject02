import entity.*;
import enum_types.OrderStatus;
import enum_types.Position;
import enum_types.Sex;
import javafx.scene.control.TableColumn;
import service.*;
import util.HibernateSessionFactory;

import java.math.BigDecimal;
import java.util.Date;
import java.util.GregorianCalendar;

public class Test {
    public static void main(String[] args) {
        ProductService productService = new ProductServiceImpl();
        OrderService orderService = new OrderServiceImpl();
        ItemService itemService = new ItemServiceImpl();
        CustomerService customerService = new CustomerServiceImpl();

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
        Product notebook = new Product("Apple", new BigDecimal(1499));
        Order order = new Order("Ivan", customer, new Date(new GregorianCalendar(2016,5,23).getTimeInMillis()), OrderStatus.OPEN, new BigDecimal(1499));
        Item item = new Item(notebook, 2, order);

        productService.add(notebook);
        customerService.add(customer);
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
