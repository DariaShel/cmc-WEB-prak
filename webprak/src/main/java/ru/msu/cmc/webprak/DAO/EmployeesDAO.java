package ru.msu.cmc.webprak.DAO;

import ru.msu.cmc.webprak.models.Employees;

import java.util.List;

public interface EmployeesDAO {
    void addEmployee(Employees employee);
    void updateEmployee(Employees employee);
    void deleteEmployee(Employees employee);

    List<Employees> getEmployeeBySurname(String employeeSurname);
    Employees getEmployeeById(Long employeeId);
    List<Employees> getEmployeeAll();
}