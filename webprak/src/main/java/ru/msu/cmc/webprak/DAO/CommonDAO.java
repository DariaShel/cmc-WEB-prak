package ru.msu.cmc.webprak.DAO;

import ru.msu.cmc.webprak.models.CommonEntity;

import java.util.Collection;

public interface CommonDAO<T extends CommonEntity<ID>, ID> {
    T getById(ID id);

    Collection<T> getAll();

    void save(T entity);

    void saveCollection(Collection<T> entities);

    void delete(T entity);

    void update(T entity);

//    void create(T entity);

//    List<T> filter(Map<String, List> filters, Class persistentClass);
//
//    List<T> sort(Map<String, String> order, Class persistentClass);
}