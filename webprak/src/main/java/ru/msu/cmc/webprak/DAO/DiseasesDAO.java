package ru.msu.cmc.webprak.DAO;

import ru.msu.cmc.webprak.models.Diseases;

import java.util.List;

public interface DiseasesDAO extends CommonDAO<Diseases, Long> {

    List<Diseases> getDiseaseByDiseaseName(String diseaseName);
}