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
    public List<Employees> getEmployeesBySurname(String employeeSurname) {
        try (Session session = sessionFactory.openSession()) {
            Query query = session.createQuery("FROM Employees WHERE surname LIKE :gotName", Employees.class)
                    .setParameter("gotName", likeExpr(employeeSurname));
            return query.getResultList().size() == 0 ? null : query.getResultList();
        }
    }

    @Override
    public Employees getSingleEmployeeBySurname(String employeeSurname) {
        List<Employees> candidates = this.getEmployeesBySurname(employeeSurname);
        return candidates == null ? null :
                candidates.size() == 1 ? candidates.get(0) : null;
    }

    @Override
    public List<Employees> getEmployeesByEducation(String employeeEducation) {
        try (Session session = sessionFactory.openSession()) {
            Query query = session.createQuery("FROM Employees WHERE education LIKE :gotName", Employees.class)
                    .setParameter("gotName", likeExpr(employeeEducation));
            return query.getResultList().size() == 0 ? null : query.getResultList();
        }
    }

    @Override
    public List<Employees> getEmployeesByWorkExperience(long employeeExperience) {
        try (Session session = sessionFactory.openSession()) {
            Query query = session.createQuery("FROM Employees WHERE work_experience=:experience", Employees.class)
                    .setParameter("experience", employeeExperience);
            return query.getResultList().size() == 0 ? null : query.getResultList();
        }
    }

    private String likeExpr(String param) {
        return "%" + param + "%";
    }

}