package ru.msu.cmc.webprak.DAO.functions;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import ru.msu.cmc.webprak.DAO.DiseasesDAO;
import ru.msu.cmc.webprak.models.Diseases;

import javax.persistence.Query;
import java.util.List;

@Repository
public class DiseasesDAOFunc extends CommonDAOFunc<Diseases, Long> implements DiseasesDAO {

    public DiseasesDAOFunc(){
        super(Diseases.class);
    }

    @Override
    public List<Diseases> getDiseaseByDiseaseName(String diseaseName) {
        try (Session session = sessionFactory.openSession()) {
            Query query = session.createQuery("FROM Diseases WHERE name_disease LIKE :gotName", Diseases.class)
                    .setParameter("gotName", likeExpr(diseaseName));
            return query.getResultList().size() == 0 ? null : query.getResultList();
        }
    }

    private String likeExpr(String param) {
        return "%" + param + "%";
    }

}