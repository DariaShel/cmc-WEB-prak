package ru.msu.cmc.webprak.DAO.functions;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import ru.msu.cmc.webprak.DAO.LabelsDAO;
import ru.msu.cmc.webprak.models.Labels;

import javax.persistence.Query;
import java.util.List;

@Repository
public class LabelsDAOFunc extends CommonDAOFunc<Labels, Long> implements LabelsDAO {

    public LabelsDAOFunc(){
        super(Labels.class);
    }

}