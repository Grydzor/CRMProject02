package TestService;

import dao.EmployeeDAO;
import dao.UserDAO;
import entity.Employee;
import entity.User;
import enum_types.Position;
import enum_types.Sex;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import service.EmployeeService;
import service.UserService;
import util.ApplicationContextFactory;

import java.util.List;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class UserServiceTest {
    private ApplicationContext context;
    private UserService userService;
    private EmployeeService employeeService;

    @Before
    public void init(){
        context = ApplicationContextFactory.getApplicationContext();
        employeeService = context.getBean("employeeService", EmployeeService.class);
        userService = context.getBean("userService", UserService.class);
    }

    @Test
    public void testRead(){
        List<User> userList = userService.findAll();
        User user = userService.read(userList.get(0).getId());

        assertNotNull("Проверка чтения первого пользователя", user);
    }


    @Test
    public void createUpdateDelete(){
        Employee employee = context.getBean(Employee.class);
        employee.setName("User");
        employee.setSurname("Userov");
        employee.setAge(12);
        employee.setSex(Sex.MALE);
        employee.setPosition(Position.ADMIN);

        Long idEmployee = employeeService.create(employee);

        User user = context.getBean(User.class);
        user.setLogin("login");
        user.setPassword("password");
        user.setEmployee(employee);


        Long idUser = userService.create(user);
        User userReturned = userService.read(idUser);

        assertEquals("Проверка корректности записаного пользователя", userReturned, user);

        User user1 = context.getBean(User.class);
        user1.setLogin("login1");
        user1.setPassword("password1");
        user1.setEmployee(employee);
        user1.setId(idUser);


        userService.update(user1);
        User userReturned1 = userService.read(idUser);

        assertEquals("Проверка корректности обновления информации о пользователе", userReturned1, user1);

        userService.delete(userReturned1);
        User userReturned2 = userService.read(idUser);
        employeeService.delete(employee);

        assertNull("Проверка корректности удаления информации о пользователе", userReturned2);
    }
}
