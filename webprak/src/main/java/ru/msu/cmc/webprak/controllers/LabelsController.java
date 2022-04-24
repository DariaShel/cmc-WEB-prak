package ru.msu.cmc.webprak.controllers;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.msu.cmc.webprak.DAO.AnimalsDAO;
import ru.msu.cmc.webprak.DAO.EmployeesDAO;
import ru.msu.cmc.webprak.DAO.LabelsDAO;
import ru.msu.cmc.webprak.models.Animals;
import ru.msu.cmc.webprak.models.Employees;
import ru.msu.cmc.webprak.models.Labels;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

@RestController
@RequestMapping(path = "api/labels")
public class LabelsController {

    @Autowired
    private LabelsDAO labelsDAO;
    @Autowired
    private AnimalsDAO animalsDAO;
    @Autowired
    private EmployeesDAO employeesDAO;

    @GetMapping("/")
    public String labelsListPage() {
        JSONArray labels = new JSONArray();
        List<Labels> table = (List<Labels>) labelsDAO.getAll();

        for (Labels singleLabel : table) {
            String installation_time, uninstallation_time;
            if (singleLabel.getInstallation_time() == null) {
                installation_time = "null";
            } else {
                installation_time = singleLabel.getInstallation_time().toString().split(" ")[0];
            }

            if (singleLabel.getUninstallation_time() == null) {
                uninstallation_time = "null";
            } else {
                uninstallation_time = singleLabel.getUninstallation_time().toString().split(" ")[0];
            }

            JSONObject label = new JSONObject();
            label.put("id_label", singleLabel.getId_label());
            label.put("id_animal", singleLabel.getId_animal().getId_animal());
            label.put("installation_time", installation_time);
            label.put("uninstallation_time", uninstallation_time);
            label.put("id_employee", singleLabel.getId_employee().getId_employee());
            label.put("id_employee", singleLabel.getId_employee().getId_employee());
            labels.add(label);
        }
        return labels.toJSONString();
    }

    @GetMapping("{id_label}")
    public String getLabel(@PathVariable("id_label") long labelId) {
        Labels singleLabel = labelsDAO.getById(labelId);
        JSONObject label = new JSONObject();
        String installation_time, uninstallation_time;
        if (singleLabel.getInstallation_time() == null) {
            installation_time = "null";
        } else {
            installation_time = singleLabel.getInstallation_time().toString().split(" ")[0];
        }

        if (singleLabel.getUninstallation_time() == null) {
            uninstallation_time = "null";
        } else {
            uninstallation_time = singleLabel.getUninstallation_time().toString().split(" ")[0];
        }
        label.put("id_label", singleLabel.getId_label());
        label.put("id_animal", singleLabel.getId_animal().getId_animal());
        label.put("installation_time", installation_time);
        label.put("uninstallation_time", uninstallation_time);
        label.put("id_employee", singleLabel.getId_employee().getId_employee());
        return label.toJSONString();
    }

    @DeleteMapping("{id_label}")
    public void removeLabel(@PathVariable("id_label") long labelId) {
        Labels delLabel = labelsDAO.getById(labelId);
        labelsDAO.delete(delLabel);
    }

    @PostMapping("/")
    public void saveLabel(@RequestBody String body) throws ParseException, java.text.ParseException {
        JSONParser parser = new JSONParser();
        JSONObject object = (JSONObject) parser.parse(body);
        Long id_label = Long.parseLong((String) object.get("id_label"));
        Labels label = labelsDAO.getById(id_label);
        Animals id_animal = animalsDAO.getById(Long.parseLong((String) object.get("id_animal")));
        Employees id_employee = employeesDAO.getById(Long.parseLong((String) object.get("id_employee")));

        DateFormat formatter = new SimpleDateFormat("yyyy-dd-MM");
        if (label != null) {
            label.setId_animal(id_animal);
            label.setInstallation_time(formatter.parse((String) object.get("installation_time")));
            label.setUninstallation_time(formatter.parse((String) object.get("uninstallation_time")));
            label.setId_employee(id_employee);
            labelsDAO.update(label);
        } else {
            label = new Labels(id_label,
                    id_animal,
                    formatter.parse((String) object.get("installation_time")),
                    formatter.parse((String) object.get("uninstallation_time")),
                    id_employee);
            labelsDAO.save(label);
        }
    }
}