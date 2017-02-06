package dao;

import entity.Employee;

import java.util.List;

@Deprecated
public interface EmployeeDAO {
    Long create(Employee employee);

    Employee read(Long id);

    Boolean update(Employee employee);

    Boolean delete(Employee employee);

    List<Employee> findAll();
}
