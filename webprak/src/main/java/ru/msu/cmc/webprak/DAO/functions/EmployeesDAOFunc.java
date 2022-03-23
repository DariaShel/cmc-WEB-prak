package ru.msu.cmc.webprak.DAO.functions;

import org.hibernate.Session;
import org.hibernate.query.Query;
import ru.msu.cmc.webprak.DAO.EmployeesDAO;
import ru.msu.cmc.webprak.models.Employees;
import ru.msu.cmc.webprak.utils.HibernateUtil;

import java.util.List;

public class EmployeesDAOFunc implements EmployeesDAO {

    @Override
    public void addEmployee(Employees employee) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(employee);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void updateEmployee(Employees employee) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.update(employee);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void deleteEmployee(Employees employee) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.delete(employee);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<Employees> getEmployeeBySurname(String employeeSurname) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query<Employees> query = session.createQuery("FROM Employees WHERE surname LIKE :gotName", Employees.class)
                .setParameter("gotName", "%" + employeeSurname + "%");
        if (query.getResultList().size() == 0) {
            return null;
        }
        return query.getResultList();
    }

    @Override
    public Employees getEmployeeById(Long employeeId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query<Employees> query = session.createQuery("FROM Employees WHERE id_employee = :param", Employees.class)
                .setParameter("param", employeeId);
        if (query.getResultList().size() == 0) {
            return null;
        }
        return query.getResultList().get(0);
    }

    @Override
    public List<Employees> getEmployeeAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query<Employees> query = session.createQuery("FROM Employees", Employees.class);
        if (query.getResultList().size() == 0) {
            return null;
        }
        return query.getResultList();
    }
}