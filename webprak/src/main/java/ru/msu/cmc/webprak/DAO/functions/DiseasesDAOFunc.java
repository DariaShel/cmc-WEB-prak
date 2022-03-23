package ru.msu.cmc.webprak.DAO.functions;

import org.hibernate.Session;
import org.hibernate.query.Query;
import ru.msu.cmc.webprak.DAO.DiseasesDAO;
import ru.msu.cmc.webprak.models.Diseases;
import ru.msu.cmc.webprak.utils.HibernateUtil;

import java.util.List;

public class DiseasesDAOFunc implements DiseasesDAO {

    @Override
    public void addDisease(Diseases disease) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(disease);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void updateDisease(Diseases disease) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.update(disease);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void deleteDisease(Diseases disease) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.delete(disease);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<Diseases> getDiseaseByName(String diseaseName) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query<Diseases> query = session.createQuery("FROM Diseases WHERE name_disease LIKE :gotName", Diseases.class)
                .setParameter("gotName", "%" + diseaseName + "%");
        if (query.getResultList().size() == 0) {
            return null;
        }
        return query.getResultList();
    }

    @Override
    public Diseases getDiseaseById(Long diseaseId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query<Diseases> query = session.createQuery("FROM Diseases WHERE id_disease = :param", Diseases.class)
                .setParameter("param", diseaseId);
        if (query.getResultList().size() == 0) {
            return null;
        }
        return query.getResultList().get(0);
    }

    @Override
    public List<Diseases> getDiseaseAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query<Diseases> query = session.createQuery("FROM Diseases", Diseases.class);
        if (query.getResultList().size() == 0) {
            return null;
        }
        return query.getResultList();
    }
}