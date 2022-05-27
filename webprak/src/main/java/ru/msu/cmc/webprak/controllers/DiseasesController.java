package ru.msu.cmc.webprak.controllers;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.msu.cmc.webprak.DAO.AnimalsDAO;
import ru.msu.cmc.webprak.DAO.EmployeesDAO;
import ru.msu.cmc.webprak.DAO.DiseasesDAO;
import ru.msu.cmc.webprak.models.Animals;
import ru.msu.cmc.webprak.models.Employees;
import ru.msu.cmc.webprak.models.Diseases;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(path = "api/diseases")
public class DiseasesController {

    @Autowired
    private DiseasesDAO diseasesDAO;
    @Autowired
    private AnimalsDAO animalsDAO;
    @Autowired
    private EmployeesDAO employeesDAO;

    @GetMapping("/")
    public String diseasesListPage() {
        JSONArray diseases = new JSONArray();
        List<Diseases> table = (List<Diseases>) diseasesDAO.getAll();

        for (Diseases singleDisease : table) {
            String time_disease, recovery;
            if (singleDisease.getTime_disease() == null) {
                time_disease = "null";
            } else {
                time_disease = singleDisease.getTime_disease().toString().split(" ")[0];
            }

            if (singleDisease.getRecovery() == null) {
                recovery = "null";
            } else {
                recovery = singleDisease.getRecovery().toString().split(" ")[0];
            }

            JSONObject disease = new JSONObject();
            disease.put("id_disease", singleDisease.getId_disease());
            disease.put("id_animal", singleDisease.getId_animal().getId_animal());
            disease.put("name_disease", singleDisease.getName_disease());
            disease.put("time_disease", time_disease);
            disease.put("recovery", recovery);
            disease.put("id_employee", singleDisease.getId_employee().getId_employee());
            disease.put("help", singleDisease.getHelp());
            disease.put("consequences", singleDisease.getConsequences());
            diseases.add(disease);
        }
        return diseases.toJSONString();
    }

    @GetMapping("{id_disease}")
    public String getDisease(@PathVariable("id_disease") long diseaseId) {
        Diseases singleDisease = diseasesDAO.getById(diseaseId);
        JSONObject disease = new JSONObject();
        String time_disease, recovery;
        if (singleDisease.getTime_disease() == null) {
            time_disease = "null";
        } else {
            time_disease = singleDisease.getTime_disease().toString().split(" ")[0];
        }

        if (singleDisease.getRecovery() == null) {
            recovery = "null";
        } else {
            recovery = singleDisease.getRecovery().toString().split(" ")[0];
        }

        disease.put("id_disease", singleDisease.getId_disease());
        disease.put("id_animal", singleDisease.getId_animal().getId_animal());
        disease.put("name_disease", singleDisease.getName_disease());
        disease.put("time_disease", time_disease);
        disease.put("recovery", recovery);
        disease.put("id_employee", singleDisease.getId_employee().getId_employee());
        disease.put("help", singleDisease.getHelp());
        disease.put("consequences", singleDisease.getConsequences());
        return disease.toJSONString();
    }

    @DeleteMapping("{id_disease}")
    public void removeDisease(@PathVariable("id_disease") long diseaseId) {
        Diseases delDisease = diseasesDAO.getById(diseaseId);
        diseasesDAO.delete(delDisease);
    }

    @PostMapping("/")
    public void saveDisease(@RequestBody String body) throws ParseException, java.text.ParseException {
        JSONParser parser = new JSONParser();
        JSONObject object = (JSONObject) parser.parse(body);
        Long id_disease = Long.parseLong((String) object.get("id_disease"));
        Diseases disease = diseasesDAO.getById(id_disease);
        Animals id_animal = animalsDAO.getById(Long.parseLong((String) object.get("id_animal")));
        Employees id_employee = employeesDAO.getById(Long.parseLong((String) object.get("id_employee")));

        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        if (disease != null) {
            disease.setId_animal(id_animal);
            disease.setName_disease((String) object.get("name_disease"));
            disease.setTime_disease(formatter.parse((String) object.get("time_disease")));
            disease.setRecovery(formatter.parse((String) object.get("recovery")));
            disease.setId_employee(id_employee);
            disease.setHelp((String) object.get("help"));
            disease.setConsequences((String) object.get("consequences"));
            diseasesDAO.update(disease);
        } else {
            disease = new Diseases(id_disease,
                    id_animal,
                    (String) object.get("name_disease"),
                    formatter.parse((String) object.get("time_disease")),
                    formatter.parse((String) object.get("recovery")),
                    id_employee,
                    (String) object.get("help"),
                    (String) object.get("consequences"));
            diseasesDAO.save(disease);
        }
    }
}