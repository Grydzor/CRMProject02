package TestService;

import entity.Employee;
import enum_types.Position;
import enum_types.Sex;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import service.EmployeeService;

import java.util.List;

import static org.junit.Assert.*;

public class EmployeeServiceTest {
    private ApplicationContext context;
    private EmployeeService employeeService;

    @Before
    public void init(){
        context = new ClassPathXmlApplicationContext(
                        "/spring/spring-config.xml");
        employeeService = context.getBean("employeeService", EmployeeService.class);
    }

    @Test
    public void testRead(){
        List<Employee> list = employeeService.findAll();
        Employee employee = employeeService.read(list.get(0).getId());

        assertNotNull("Проверка чтения первого работника", employee);
    }

    @Test
    public void createUpdateDelete(){
        Employee employee = context.getBean(Employee.class);
        employee.setName("Ivan");
        employee.setSurname("Ivanov");
        employee.setAge(12);
        employee.setSex(Sex.MALE);
        employee.setPosition(Position.ADMIN);

        Long id = employeeService.create(employee);
        Employee employeeReturned = employeeService.read(id);

        assertEquals("Проверка корректности записаного сотрудника", employeeReturned.toString(), employee.toString());

        Employee employee1 = context.getBean(Employee.class);
        employee1.setName("Ivan");
        employee1.setSurname("Ivanov");
        employee1.setAge(12);
        employee1.setSex(Sex.MALE);
        employee1.setPosition(Position.ADMIN);
        employee1.setId(id);


        employeeService.update(employee1);
        Employee employeeReturned1 = employeeService.read(id);

        assertEquals("Проверка корректности обновления информации о сотруднике", employeeReturned1.toString(), employee1.toString());

        employeeService.delete(employeeReturned1);
        Employee employeeReturned2 = employeeService.read(id);

        assertNull("Проверка корректности удаления информации о сотруднике", employeeReturned2);
    }
}
