package ru.msu.cmc.webprak.DAO;

import ru.msu.cmc.webprak.models.Labels;

import java.util.List;

public interface LabelsDAO {
    void addLabel(Labels label);
    void updateLabel(Labels label);
    void deleteLabel(Labels label);

    Labels getLabelById(Long labelId);
    List<Labels> getLabelAll();
}