package ru.msu.cmc.webprak.DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import ru.msu.cmc.webprak.models.Employees;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(locations="classpath:application.properties")
public class EmployeesDAOTest {

    @Autowired
    private EmployeesDAO employeesDAO;
    @Autowired
    private SessionFactory sessionFactory;

    @Test
    void testSimpleManipulations() {
        List<Employees> employeesListAll = (List<Employees>) employeesDAO.getAll();
        assertEquals(3, employeesListAll.size());

        Employees employeesNotExist = employeesDAO.getById(100L);
        assertNull(employeesNotExist);

        List<Employees> surnameQuery = employeesDAO.getEmployeesBySurname("Kamnev");
        assertEquals(1, surnameQuery.size());
        assertEquals("Kamnev", surnameQuery.get(0).getSurname());

        List<Employees> eduQuery = employeesDAO.getEmployeesByEducation("secondary education");
        assertEquals(3, eduQuery.size());
        assertEquals("secondary education", eduQuery.get(0).getEducation());

        List<Employees> expQuery = employeesDAO.getEmployeesByWorkExperience(5L);
        assertEquals(1, expQuery.size());
        assertEquals(5L, expQuery.get(0).getWork_experience());
    }

    @Test
    void testUpdate() {
        String surname = "Smirnov", name = "Vladimir", patronymic = "Alexandrovich", education = "higher education";
        long work_experience = 5;
        String animal_species = "some species", help = "some help", marked = "list of animals", photo = "worker.jpg";

        Employees updateEmployees = employeesDAO.getById(1L);
        updateEmployees.setSurname(surname);
        updateEmployees.setName(name);
        updateEmployees.setPatronymic(patronymic);
        updateEmployees.setEducation(education);
        updateEmployees.setWork_experience(work_experience);
        updateEmployees.setAnimal_species(animal_species);
        updateEmployees.setHelp(help);
        updateEmployees.setMarked(marked);
        updateEmployees.setPhoto(photo);
        employeesDAO.update(updateEmployees);

        Employees test = employeesDAO.getById(1L);
        assertEquals(surname, test.getSurname());
        assertEquals(name, test.getName());
        assertEquals(patronymic, test.getPatronymic());
        assertEquals(education, test.getEducation());
        assertEquals(work_experience, test.getWork_experience());
        assertEquals(animal_species, test.getAnimal_species());
        assertEquals(help, test.getHelp());
        assertEquals(marked, test.getMarked());
        assertEquals(photo, test.getPhoto());
    }

    @Test
    void testDelete() {
        Employees deleteEmployees = employeesDAO.getSingleEmployeeBySurname("Ivanov");
        employeesDAO.delete(deleteEmployees);

        Employees ivanov = employeesDAO.getSingleEmployeeBySurname("Ivanov");
        assertNull(ivanov);
    }

    @BeforeEach
    void beforeEach() {
        List<Employees> employeesList = new ArrayList<>();
        employeesList.add(new Employees(
                0L, "Kostin", "Andrey", "Igorevich", "secondary education", 4, "[\"C. niloticus\"]", "[1]", "[1]", "images/emp1.jpg"
        ));
        employeesList.add(new Employees(
                1L, "Ivanov", "Alexey", "Alexandrivich", "secondary education", 3, "[\"C. niloticus\"]", "[2]", "[2]", "images/emp2.jpg"
        ));
        employeesList.add(new Employees(
                2L, "Kamnev", "Boris", "Olegovich", "secondary education", 5, "[\"C. niloticus\"]", "[1, 2, 3]", "[3]", "images/emp3.jpg"
        ));

        employeesDAO.saveCollection(employeesList);
    }

    @BeforeAll
    @AfterEach
    void annihilation() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery("TRUNCATE employees RESTART IDENTITY CASCADE;").executeUpdate();
            session.createSQLQuery("ALTER SEQUENCE employees_id_employee_seq RESTART WITH 1;").executeUpdate();
            session.getTransaction().commit();
        }
    }
}