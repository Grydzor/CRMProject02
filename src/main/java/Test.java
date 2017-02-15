import entity.Employee;
import entity.Order;
import entity.Product;
import entity.User;
import enum_types.OrderStatus;
import enum_types.Position;
import enum_types.Sex;
import service.*;
import util.HibernateSessionFactory;

import java.sql.ResultSet;
import java.util.Date;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        Service service = new ServiceImpl();

        /*Employee employee = new Employee("Steve", "Jobs", 20, Sex.MALE, Position.ADMIN);
        User user = new User("root", "root", employee);

        service.add(employee);
        service.add(user);

        Employee employeeFrom = service.read(Employee.class, 1L);
        User userFrom = service.read(User.class, 1L);

        System.out.println(employeeFrom.getName() + ", " + employeeFrom.getSurname() + ", " + employeeFrom.getAge());
        System.out.println(userFrom.getLogin() + ", " + userFrom.getPassword() + ", " + userFrom.getEmployee().getName());*/

        Product notebook = new Product("Apple", 1499);
        Order order = new Order("Ivan", "Taras", new Date(2016, 6, 12), OrderStatus.OPEN, 1499);
        entity.Position position = new entity.Position(notebook, 2999, 2, order);

        service.add(notebook);
        service.add(order);
        service.add(position);

        /*List<Order> orders = service.findAll(Order.class);
        for (Order order : orders) {
            List<entity.Position> positions = (List<entity.Position>) order.getPositions();
            if (!positions.isEmpty()) {
                for (entity.Position position : positions) {
                    System.out.println(position);
                }
            }
        }*/
        HibernateSessionFactory.getSessionFactory().close();
    }
}
