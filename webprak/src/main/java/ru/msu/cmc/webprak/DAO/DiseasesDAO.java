package ru.msu.cmc.webprak.DAO;

import ru.msu.cmc.webprak.models.Diseases;

import java.util.List;

public interface DiseasesDAO {
    void addDisease(Diseases disease);
    void updateDisease(Diseases disease);
    void deleteDisease(Diseases disease);

    List<Diseases> getDiseaseByName(String diseaseName);
    Diseases getDiseaseById(Long diseaseId);
    List<Diseases> getDiseaseAll();
}