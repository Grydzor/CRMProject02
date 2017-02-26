package TestDAO;

import dao.EmployeeDAO;
import dao.UserDAO;
import entity.Employee;
import entity.User;
import enum_types.Position;
import enum_types.Sex;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import util.ApplicationContextFactory;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class UserDAOTest {
    private ApplicationContext context;
    private UserDAO userDAO;
    private EmployeeDAO employeeDAO;

    @Before
    public void init(){
        context = ApplicationContextFactory.getApplicationContext();
        employeeDAO = context.getBean("employeeDAO", EmployeeDAO.class);
        userDAO = context.getBean("userDAO", UserDAO.class);
    }

    @Test
    public void testRead(){
        User user = userDAO.read(new Long("1"));

        assertNotNull("Проверка чтения первого пользователя", user);
    }


    @Test
    public void CheckUserDAO(){
        Employee employee = context.getBean(Employee.class);
        employee.setName("User");
        employee.setSurname("Userov");
        employee.setAge(12);
        employee.setSex(Sex.MALE);
        employee.setPosition(Position.ADMIN);

        Long idEmployee = employeeDAO.create(employee);

        User user = context.getBean(User.class);
        user.setLogin("login");
        user.setPassword("password");
        user.setEmployee(employee);


        Long idUser = userDAO.create(user);
        User userReturned = userDAO.read(idUser);

        assertEquals("Проверка корректности записаного пользователя", userReturned, user);

        User user1 = context.getBean(User.class);
        user1.setLogin("login1");
        user1.setPassword("password1");
        user1.setEmployee(employee);
        user1.setId(idUser);


        userDAO.update(user1);
        User userReturned1 = userDAO.read(idUser);

        assertEquals("Проверка корректности обновления информации о пользователе", userReturned1, user1);

        userDAO.delete(userReturned1);
        User userReturned2 = userDAO.read(idUser);
        employeeDAO.delete(employee);

        assertNull("Проверка корректности удаления информации о пользователе", userReturned2);
    }
}
