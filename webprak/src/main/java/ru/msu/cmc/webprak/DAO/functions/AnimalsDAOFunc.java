package ru.msu.cmc.webprak.DAO.functions;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import ru.msu.cmc.webprak.DAO.AnimalsDAO;
import ru.msu.cmc.webprak.models.Animals;

import javax.persistence.Query;
import java.util.List;

@Repository
public class AnimalsDAOFunc extends CommonDAOFunc<Animals, Long> implements AnimalsDAO {

    public AnimalsDAOFunc(){
        super(Animals.class);
    }

    @Override
    public List<Animals> getAnimalsByName(String animalName) {
        try (Session session = sessionFactory.openSession()) {
            Query query = session.createQuery("FROM Animals WHERE name LIKE :gotName", Animals.class)
                    .setParameter("gotName", likeExpr(animalName));
            return query.getResultList().size() == 0 ? null : query.getResultList();
        }
    }

    @Override
    public Animals getSingleAnimalByName(String animalName) {
        List<Animals> candidates = this.getAnimalsByName(animalName);
        return candidates == null ? null :
                candidates.size() == 1 ? candidates.get(0) : null;
    }

    @Override
    public List<Animals> getAnimalsBySpecies(String animalSpecies) {
        try (Session session = sessionFactory.openSession()) {
            Query query = session.createQuery("FROM Animals WHERE species LIKE :gotName", Animals.class)
                    .setParameter("gotName", likeExpr(animalSpecies));
            return query.getResultList().size() == 0 ? null : query.getResultList();
        }
    }

    @Override
    public List<Animals> getAnimalsByLatinName(String latinName) {
        try (Session session = sessionFactory.openSession()) {
            Query query = session.createQuery("FROM Animals WHERE latin_name LIKE :gotName", Animals.class)
                    .setParameter("gotName", likeExpr(latinName));
            return query.getResultList().size() == 0 ? null : query.getResultList();
        }
    }

    private String likeExpr(String param) {
        return "%" + param + "%";
    }

}