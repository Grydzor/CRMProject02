package TestDAO;

import dao.EmployeeDAO;
import entity.Employee;
import enum_types.Position;
import enum_types.Sex;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class EmployeeDAOTest {
    ApplicationContext context;
    EmployeeDAO employeeDAO;

    @Before
    public void init(){
        context = new ClassPathXmlApplicationContext(
                        "/spring/spring-config.xml");
        employeeDAO= (EmployeeDAO) context.getBean("employeeDAO");
    }

    @Test
    public void testRead(){


        Employee employee = employeeDAO.read(1L);
        System.out.println(employee.toString());

        assertNotNull("Проверка чтения первого работника", employee);
    }

    @Test
    public void testFailRead(){
        Employee employee = employeeDAO.read(new Long("1000"));

        assertNull("Проверка чтения 1000 работника", employee);
    }

    @Test
    public void CheckEmployeeDAO(){
        Employee employee = (Employee) context.getBean("employee");
        employee.setName("Ivan");
        employee.setSurname("Ivanov");
        employee.setAge(12);
        employee.setSex(Sex.MALE);
        employee.setPosition(Position.ADMIN);

        Long id = employeeDAO.create(employee);
        Employee employeeReturned = employeeDAO.read(id);

        assertEquals("Проверка корректности записаного сотрудника", employeeReturned.toString(), employee.toString());

        Employee employee1 = (Employee) context.getBean("employee");
        employee1.setName("Ivan");
        employee1.setSurname("Ivanov");
        employee1.setAge(12);
        employee1.setSex(Sex.MALE);
        employee1.setPosition(Position.ADMIN);
        employee1.setId(id);


        System.out.println(employee1.toString());

        employeeDAO.update(employee1);
        Employee employeeReturned1 = employeeDAO.read(id);

        assertEquals("Проверка корректности обновления информации о сотруднике", employeeReturned1.toString(), employee1.toString());

        employeeDAO.delete(employeeReturned1);
        Employee employeeReturned2 = employeeDAO.read(id);

        assertNull("Проверка корректности удаления информации о сотруднике", employeeReturned2);
    }
}
