package ru.msu.cmc.webprak.DAO;

import ru.msu.cmc.webprak.models.Animals;

import java.util.List;

public interface AnimalsDAO extends CommonDAO<Animals, Long> {
    List<Animals> getAnimalsByName(String animalName);

    Animals getSingleAnimalByName(String animalName);

    List<Animals> getAnimalsBySpecies(String animalSpecies);

    List<Animals> getAnimalsByLatinName(String latinName);
}