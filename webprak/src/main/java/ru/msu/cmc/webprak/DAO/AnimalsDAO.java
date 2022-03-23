package ru.msu.cmc.webprak.DAO;

import ru.msu.cmc.webprak.models.Animals;

import java.util.List;

public interface AnimalsDAO {
    void addAnimal(Animals animal);
    void updateAnimal(Animals animal);
    void deleteAnimal(Animals animal);

    List<Animals> getAnimalByName(String animalName);
    Animals getAnimalById(Long animalId);
    List<Animals> getAnimalAll();
}