package ru.msu.cmc.webprak.DAO.functions;

import org.hibernate.Session;
import org.hibernate.query.Query;
import ru.msu.cmc.webprak.DAO.LabelsDAO;
import ru.msu.cmc.webprak.models.Labels;
import ru.msu.cmc.webprak.utils.HibernateUtil;

import java.util.List;

public class LabelsDAOFunc implements LabelsDAO {

    @Override
    public void addLabel(Labels label) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(label);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void updateLabel(Labels label) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.update(label);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void deleteLabel(Labels label) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.delete(label);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public Labels getLabelById(Long labelId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query<Labels> query = session.createQuery("FROM Labels WHERE id_label = :param", Labels.class)
                .setParameter("param", labelId);
        if (query.getResultList().size() == 0) {
            return null;
        }
        return query.getResultList().get(0);
    }

    @Override
    public List<Labels> getLabelAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query<Labels> query = session.createQuery("FROM Labels", Labels.class);
        if (query.getResultList().size() == 0) {
            return null;
        }
        return query.getResultList();
    }
}