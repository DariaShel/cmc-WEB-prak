package ru.msu.cmc.webprak.DAO.functions;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import ru.msu.cmc.webprak.DAO.CommonDAO;
import ru.msu.cmc.webprak.models.CommonEntity;

import javax.persistence.criteria.CriteriaQuery;
import java.io.Serializable;
import java.util.Collection;

@Repository
public abstract class CommonDAOFunc<T extends CommonEntity<ID>, ID extends Serializable> implements CommonDAO<T, ID> {

    protected SessionFactory sessionFactory;

    protected Class<T> persistentClass;

    public CommonDAOFunc(Class<T> entityClass){
        this.persistentClass = entityClass;
    }

    @Autowired
    public void setSessionFactory(LocalSessionFactoryBean sessionFactory) {
        this.sessionFactory = sessionFactory.getObject();
    }

    @Override
    public T getById(ID id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(persistentClass, id);
        }
    }

    @Override
    public Collection<T> getAll() {
        try (Session session = sessionFactory.openSession()) {
            CriteriaQuery<T> criteriaQuery = session.getCriteriaBuilder().createQuery(persistentClass);
            criteriaQuery.from(persistentClass);
            return session.createQuery(criteriaQuery).getResultList();
        }
    }

    @Override
    public void save(T entity) {
        try (Session session = sessionFactory.openSession()) {
            if (entity.getId() != null) {
                entity.setId(null);
            }
            session.beginTransaction();
            session.saveOrUpdate(entity);
            session.getTransaction().commit();
        }
    }

    @Override
    public void saveCollection(Collection<T> entities) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            for (T entity : entities) {
                this.save(entity);
            }
            session.getTransaction().commit();
        }
    }

    @Override
    public void update(T entity) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(entity);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(T entity) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(entity);
            session.getTransaction().commit();
        }
    }

//    @Override
//    public void create(T entity) {
//        try (Session session = sessionFactory.openSession()) {
//            session.beginTransaction();
//            session.save(entity);
//            session.getTransaction().commit();
//            session.close();
//        }
//    }
//
//    @Override
//    public List<T> filter(Map<String,List> filters, Class persistentClass) {
//        try (Session session = sessionFactory.openSession()) {
//            filters.entrySet().forEach(entry -> {
//                String filterName = entry.getKey();
//                List parameters = entry.getValue();
//                Filter enableFilter = session.enableFilter(filterName);
//                Set<String> paramNames = enableFilter.getFilterDefinition().getParameterNames();
//                AtomicInteger i = new AtomicInteger();
//                paramNames.forEach(name ->
//                        enableFilter.setParameter(name, parameters.get(i.getAndIncrement()))
//                );
//            });
//
//            Query query = session.createQuery("FROM " + persistentClass.getName());
//
//            @SuppressWarnings("unchecked")
//            List<?> result = ((org.hibernate.query.Query<?>) query).list();
//            session.close();
//            return (List<T>) result;
//        }
//    }
//
//    @Override
//    public List<T> sort(Map<String, String> order, Class persistentClass) {
//        try (Session session = sessionFactory.openSession()) {
//            Criteria criteria = session.createCriteria(persistentClass, "CRITERIA");
//            order.forEach((key, value) -> {
//                if (value.equals("asc")) {
//                    criteria.addOrder(Order.asc(key));
//                } else if (value.equals("desc")) {
//                    criteria.addOrder(Order.desc(key));
//                }
//            });
//            return criteria.list();
//        }
//    }
}