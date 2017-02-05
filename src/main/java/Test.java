import entity.Employee;
import entity.User;
import enum_types.Position;
import enum_types.Sex;
import service.EmployeeService;
import service.EmployeeServiceImpl;
import service.UserService;
import service.UserServiceImpl;
import util.HibernateSessionFactory;

/**
 * Created by eriol4ik on 05/02/2017.
 */
public class Test {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        EmployeeService employeeService = new EmployeeServiceImpl();

        Employee employee = new Employee("Name1", "Surname1", 20, Sex.MALE, Position.ADMIN);
        User user = new User("login1", "pass1", employee);

        employeeService.add(employee);
        userService.add(user);

        HibernateSessionFactory.getSessionFactory().close();
    }
}
