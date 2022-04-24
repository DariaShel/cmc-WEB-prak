package ru.msu.cmc.webprak.controllers;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.msu.cmc.webprak.DAO.EmployeesDAO;
import ru.msu.cmc.webprak.models.Employees;

import java.util.List;

@RestController
@RequestMapping(path = "api/employees")
public class EmployeesController {

    @Autowired
    private EmployeesDAO employeesDAO;

    @GetMapping("/")
    public String employeesListPage() {
        JSONArray employees = new JSONArray();
        List<Employees> table = (List<Employees>) employeesDAO.getAll();

        for (Employees singleEmployee : table) {
            JSONObject employee = new JSONObject();
            employee.put("id_employee", singleEmployee.getId_employee());
            employee.put("surname", singleEmployee.getSurname());
            employee.put("name", singleEmployee.getName());
            employee.put("patronymic", singleEmployee.getPatronymic());
            employee.put("education", singleEmployee.getEducation());
            employee.put("work_experience", singleEmployee.getWork_experience());
            employee.put("animal_species", singleEmployee.getAnimal_species());
            employee.put("help", singleEmployee.getHelp());
            employee.put("marked", singleEmployee.getMarked());
            employee.put("photo", singleEmployee.getPhoto());
            employees.add(employee);
        }
        return employees.toJSONString();
    }

    @GetMapping("{id_employee}")
    public String getEmployee(@PathVariable("id_employee") long employeeId) {
        Employees singleEmployee = employeesDAO.getById(employeeId);
        JSONObject employee = new JSONObject();
        employee.put("id_employee", singleEmployee.getId_employee());
        employee.put("surname", singleEmployee.getSurname());
        employee.put("name", singleEmployee.getName());
        employee.put("patronymic", singleEmployee.getPatronymic());
        employee.put("education", singleEmployee.getEducation());
        employee.put("work_experience", singleEmployee.getWork_experience());
        employee.put("animal_species", singleEmployee.getAnimal_species());
        employee.put("help", singleEmployee.getHelp());
        employee.put("marked", singleEmployee.getMarked());
        employee.put("photo", singleEmployee.getPhoto());
        return employee.toJSONString();
    }

    @DeleteMapping("{id_employee}")
    public void removeEmployee(@PathVariable("id_employee") long employeeId) {
        Employees delEmployee = employeesDAO.getById(employeeId);
        employeesDAO.delete(delEmployee);
    }

    @PostMapping("/")
    public void saveEmployee(@RequestBody String body) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject object = (JSONObject) parser.parse(body);
        Long id_employee = Long.parseLong((String) object.get("id_employee"));
        Employees employee = employeesDAO.getById(id_employee);

        if (employee != null) {
            employee.setSurname((String) object.get("surname"));
            employee.setName((String) object.get("name"));
            employee.setPatronymic((String) object.get("patronymic"));
            employee.setEducation((String) object.get("education"));
            employee.setWork_experience(Long.parseLong((String) object.get("work_experience")));
            employee.setAnimal_species((String) object.get("animal_species"));
            employee.setHelp((String) object.get("help"));
            employee.setMarked((String) object.get("marked"));
            employee.setPhoto((String) object.get("photo"));
            employeesDAO.update(employee);
        } else {
            employee = new Employees(id_employee,
                    (String) object.get("surname"),
                    (String) object.get("name"),
                    (String) object.get("patronymic"),
                    (String) object.get("education"),
                    Long.parseLong((String) object.get("work_experience")),
                    (String) object.get("animal_species"),
                    (String) object.get("help"),
                    (String) object.get("marked"),
                    (String) object.get("photo"));
            employeesDAO.save(employee);
        }
    }
}