package ru.msu.cmc.webprak.DAO.functions;

import org.hibernate.Session;
import org.hibernate.query.Query;
import ru.msu.cmc.webprak.DAO.AnimalsDAO;
import ru.msu.cmc.webprak.models.Animals;
import ru.msu.cmc.webprak.utils.HibernateUtil;

import java.util.List;

public class AnimalsDAOFunc implements AnimalsDAO {

    @Override
    public void addAnimal(Animals animal) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(animal);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void updateAnimal(Animals animal) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.update(animal);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void deleteAnimal(Animals animal) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.delete(animal);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<Animals> getAnimalByName(String animalName) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query<Animals> query = session.createQuery("FROM Animals WHERE name LIKE :gotName", Animals.class)
                .setParameter("gotName", "%" + animalName + "%");
        if (query.getResultList().size() == 0) {
            return null;
        }
        return query.getResultList();
    }

    @Override
    public Animals getAnimalById(Long animalId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query<Animals> query = session.createQuery("FROM Animals WHERE id_animal = :param", Animals.class)
                .setParameter("param", animalId);
        if (query.getResultList().size() == 0) {
            return null;
        }
        return query.getResultList().get(0);
    }

    @Override
    public List<Animals> getAnimalAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query<Animals> query = session.createQuery("FROM Animals", Animals.class);
        if (query.getResultList().size() == 0) {
            return null;
        }
        return query.getResultList();
    }
}