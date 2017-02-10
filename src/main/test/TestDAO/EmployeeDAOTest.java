package TestDAO;

import dao.EmployeeDAO;
import dao.EmployeeDAOImpl;
import entity.Employee;
import enum_types.Position;
import enum_types.Sex;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class EmployeeDAOTest {
    @Test
    public void testRead(){
        EmployeeDAO employeeDAO = new EmployeeDAOImpl();
        Employee employee = employeeDAO.read(new Long("1"));

        assertNotNull("Проверка чтения первого работника", employee);
    }

    @Test
    public void testFailRead(){
        EmployeeDAO employeeDAO = new EmployeeDAOImpl();
        Employee employee = employeeDAO.read(new Long("1000"));

        assertNull("Проверка чтения 1000 работника", employee);
    }

    @Test
    public void CheckEmployeeDAO(){
        EmployeeDAO employeeDAO = new EmployeeDAOImpl();
        Employee employee = new Employee("Ivan", "Ivanov", 12, Sex.MALE, Position.ADMIN);
        Long id = employeeDAO.create(employee);
        Employee employeeReturned = employeeDAO.read(id);

        assertEquals("Проверка корректности записаного сотрудника", employeeReturned.toString(), employee.toString());

        Employee employee1 = new Employee("Ivan1", "Ivanov1", 112, Sex.MALE, Position.ADMIN);
        employee1.setId(id);

        employeeDAO.update(employee1);
        Employee employeeReturned1 = employeeDAO.read(id);

        assertEquals("Проверка корректности обновления информации о сотруднике", employeeReturned1.toString(), employee1.toString());

        employeeDAO.delete(employeeReturned1);
        Employee employeeReturned2 = employeeDAO.read(id);

        assertNull("Проверка корректности удаления информации о сотруднике", employeeReturned2);
    }
}
