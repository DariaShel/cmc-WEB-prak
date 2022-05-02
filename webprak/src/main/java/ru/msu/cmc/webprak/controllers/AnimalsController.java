package ru.msu.cmc.webprak.controllers;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.msu.cmc.webprak.DAO.AnimalsDAO;
import ru.msu.cmc.webprak.models.Animals;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(path = "api/animals")
public class AnimalsController {

    @Autowired
    private AnimalsDAO animalsDAO;

    @GetMapping("/")
    public String animalsListPage() {
        JSONArray animals = new JSONArray();
        List<Animals> table = (List<Animals>) animalsDAO.getAll();

        for (Animals singleAnimal : table) {
            JSONObject animal = new JSONObject();
            animal.put("id_animal", singleAnimal.getId_animal());
            animal.put("name", singleAnimal.getName());
            animal.put("type", singleAnimal.getType());
            animal.put("class", singleAnimal.getClass_field());
            animal.put("family", singleAnimal.getFamily());
            animal.put("species", singleAnimal.getSpecies());
            animal.put("latin_name", singleAnimal.getLatin_name());
            animal.put("status", singleAnimal.getStatus());
            animal.put("migrations", singleAnimal.getMigrations());
            animal.put("appearance", singleAnimal.getAppearance());
            animal.put("behaviour", singleAnimal.getBehavior());
            animal.put("communications", singleAnimal.getCommunications());
            animal.put("photo", singleAnimal.getPhoto());
            animals.add(animal);
        }
        return animals.toJSONString();
    }

    @GetMapping("{id_animal}")
    public String getAnimal(@PathVariable("id_animal") long animalId) {
        Animals singleAnimal = animalsDAO.getById(animalId);
        JSONObject animal = new JSONObject();
        animal.put("id_animal", singleAnimal.getId_animal());
        animal.put("name", singleAnimal.getName());
        animal.put("type", singleAnimal.getType());
        animal.put("class", singleAnimal.getClass_field());
        animal.put("family", singleAnimal.getFamily());
        animal.put("species", singleAnimal.getSpecies());
        animal.put("latin_name", singleAnimal.getLatin_name());
        animal.put("status", singleAnimal.getStatus());
        animal.put("migrations", singleAnimal.getMigrations());
        animal.put("appearance", singleAnimal.getAppearance());
        animal.put("behaviour", singleAnimal.getBehavior());
        animal.put("communications", singleAnimal.getCommunications());
        animal.put("photo", singleAnimal.getPhoto());
        return animal.toJSONString();
    }

    @DeleteMapping("{id_animal}")
    public void removeAnimal(@PathVariable("id_animal") long animalId) {
        Animals delAnimal = animalsDAO.getById(animalId);
        animalsDAO.delete(delAnimal);
    }

    @PostMapping("/")
    public void saveAnimal(@RequestBody String body) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject object = (JSONObject) parser.parse(body);
        Long id_animal = Long.parseLong((String) object.get("id_animal"));
        Animals animal = animalsDAO.getById(id_animal);

        Animals.StatusType status;
        Animals.MigrationsType migrate;
        if (Long.parseLong((String) object.get("status")) == 0) {
            status = Animals.StatusType.DEAD;
        } else {
            status = Animals.StatusType.ALIVE;
        }
        if (Long.parseLong((String) object.get("migrations")) == 0) {
            migrate = Animals.MigrationsType.NO_MIGRATING;
        } else {
            migrate = Animals.MigrationsType.MIGRATING;
        }

        if (animal != null) {
            animal.setName((String) object.get("name"));
            animal.setType((String) object.get("type"));
            animal.setClass_field((String) object.get("class"));
            animal.setFamily((String) object.get("family"));
            animal.setSpecies((String) object.get("species"));
            animal.setLatin_name((String) object.get("latin_name"));
            animal.setStatus(status);
            animal.setMigrations(migrate);
            animal.setAppearance((String) object.get("appearance"));
            animal.setBehavior((String) object.get("behaviour"));
            animal.setCommunications((String) object.get("communications"));
            animal.setPhoto((String) object.get("photo"));
            animalsDAO.update(animal);
        } else {
            animal = new Animals(id_animal,
                    (String) object.get("name"),
                    (String) object.get("type"),
                    (String) object.get("class"),
                    (String) object.get("family"),
                    (String) object.get("species"),
                    (String) object.get("latin_name"),
                    status,
                    migrate,
                    (String) object.get("appearance"),
                    (String) object.get("communications"),
                    (String) object.get("communications"),
                    (String) object.get("photo"));
            animalsDAO.save(animal);
        }
    }
}