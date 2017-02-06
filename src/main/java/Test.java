import entity.Employee;
import entity.User;
import enum_types.Position;
import enum_types.Sex;
import service.*;
import util.HibernateSessionFactory;

public class Test {
    public static void main(String[] args) {
        Service service = new ServiceImpl();

        Employee employee = new Employee("Name1", "Surname1", 20, Sex.MALE, Position.ADMIN);
        User user = new User("login1", "pass1", employee);

        service.add(employee);
        service.add(user);

        Employee employeeFrom = service.read(Employee.class, 1L);
        User userFrom = service.read(User.class, 1L);

        System.out.println(employeeFrom.getName() + ", " + employeeFrom.getSurname() + ", " + employeeFrom.getAge());
        System.out.println(userFrom.getLogin() + ", " + userFrom.getPassword() + ", " + userFrom.getEmployee().getName());

        HibernateSessionFactory.getSessionFactory().close();
    }
}
