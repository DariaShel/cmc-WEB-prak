package ru.msu.cmc.webprak.DAO;

import ru.msu.cmc.webprak.models.Employees;

import java.util.List;

public interface EmployeesDAO extends CommonDAO<Employees, Long> {

    List<Employees> getEmployeeByName(String employeeName);

    List<Employees> getEmployeesBySurname(String employeeSurname);
}