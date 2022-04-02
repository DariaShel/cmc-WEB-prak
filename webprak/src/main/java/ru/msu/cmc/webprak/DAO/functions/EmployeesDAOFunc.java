package ru.msu.cmc.webprak.DAO.functions;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import ru.msu.cmc.webprak.DAO.EmployeesDAO;
import ru.msu.cmc.webprak.models.Employees;

import javax.persistence.Query;
import java.util.List;

@Repository
public class EmployeesDAOFunc extends CommonDAOFunc<Employees, Long> implements EmployeesDAO {

    public EmployeesDAOFunc(){
        super(Employees.class);
    }

    @Override
    public List<Employees> getEmployeeByName(String employeeName) {
        try (Session session = sessionFactory.openSession()) {
            Query query = session.createQuery("FROM Employees WHERE name LIKE :gotName", Employees.class)
                    .setParameter("gotName", likeExpr(employeeName));
            return query.getResultList().size() == 0 ? null : query.getResultList();
        }
    }

    @Override
    public List<Employees> getEmployeesBySurname(String employeeSurname) {
        try (Session session = sessionFactory.openSession()) {
            Query query = session.createQuery("FROM Employees WHERE surname LIKE :gotName", Employees.class)
                    .setParameter("gotName", likeExpr(employeeSurname));
            return query.getResultList().size() == 0 ? null : query.getResultList();
        }
    }

    private String likeExpr(String param) {
        return "%" + param + "%";
    }

}