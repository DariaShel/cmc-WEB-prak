package ru.msu.cmc.webprak.DAO;

import ru.msu.cmc.webprak.models.Employees;

import java.util.List;

public interface EmployeesDAO extends CommonDAO<Employees, Long> {
    List<Employees> getEmployeesBySurname(String employeeSurname);

    Employees getSingleEmployeeBySurname(String employeeSurname);

    List<Employees> getEmployeesByEducation(String employeeEducation);

    List<Employees> getEmployeesByWorkExperience(long employeeExperience);
}