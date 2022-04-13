package ru.msu.cmc.webprak.DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import ru.msu.cmc.webprak.models.Animals;
import ru.msu.cmc.webprak.models.Employees;
import ru.msu.cmc.webprak.models.Labels;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(locations="classpath:application.properties")
public class LabelsDAOTest {

    @Autowired
    private LabelsDAO labelsDAO;
    @Autowired
    private AnimalsDAO animalsDAO;
    @Autowired
    private EmployeesDAO employeesDAO;
    @Autowired
    private SessionFactory sessionFactory;

    @Test
    void testSimpleManipulations() {
        List<Labels> labelsListAll = (List<Labels>) labelsDAO.getAll();
        assertEquals(3, labelsListAll.size());

        Labels labelsNotExist = labelsDAO.getById(100L);
        assertNull(labelsNotExist);
    }

    @Test
    void testUpdate() {
        Animals idAnimal = animalsDAO.getById(1L);
        Employees idEmployee = employeesDAO.getById(1L);
        Date unistallation_time = new Date(115, Calendar.OCTOBER, 4);
        Date installation_time = new Date(120, Calendar.SEPTEMBER, 20);

        Labels updateLabels = labelsDAO.getById(2L);
        updateLabels.setId_animal(idAnimal);
        updateLabels.setInstallation_time(installation_time);
        updateLabels.setUninstallation_time(unistallation_time);
        updateLabels.setId_employee(idEmployee);
        labelsDAO.update(updateLabels);

        Labels test = labelsDAO.getById(2L);
        assertEquals(idAnimal.getId_animal(), test.getId_animal().getId_animal());
        assertEquals(installation_time, test.getInstallation_time());
        assertEquals(unistallation_time, test.getUninstallation_time());
        assertEquals(idEmployee.getId_employee(), test.getId_employee().getId_employee());
    }

    @Test
    void testDelete() {
        Labels deleteLabels = labelsDAO.getById(2L);
        labelsDAO.delete(deleteLabels);

        Labels label = labelsDAO.getById(2L);
        assertNull(label);
    }

    @BeforeEach
    void beforeEach() {
        List<Animals> animalsList = new ArrayList<>();
        List<Employees> employeesList = new ArrayList<>();
        List<Labels> labelsList = new ArrayList<>();
        animalsList.add(new Animals(
                1L, "Natasha", "Chordata", "Reptilia", "Crocodylidae", "C. niloticus", "Crocodylus niloticus", Animals.StatusType.ALIVE,
                Animals.MigrationsType.NO_MIGRATING, "It has short legs located on the sides of the body, scaly skin covered with rows of bone plates, a long strong tail and powerful jaws.",
                "Inactive animals. More than half of the crocodiles observed in nature are basking on the shore or in shallow water from early morning until dusk, keeping their jaws open to avoid overheating.",
                "{\"mother\": -1, \"father\": -1, \"child\": [3]}", "images/croc1.jpg"
        ));
        animalsList.add(new Animals(
                2L, "Gena", "Chordata", "Reptilia", "Crocodylidae", "C. niloticus", "Crocodylus niloticus", Animals.StatusType.ALIVE,
                Animals.MigrationsType.NO_MIGRATING, "It has short legs located on the sides of the body, scaly skin covered with rows of bone plates, a long strong tail and powerful jaws.",
                "Inactive animals. More than half of the crocodiles observed in nature are basking on the shore or in shallow water from early morning until dusk, keeping their jaws open to avoid overheating.",
                "{\"mother\": -1, \"father\": -1, \"child\": [3]}", "images/croc2.jpg"
        ));
        animalsList.add(new Animals(
                3L, "Clint", "Chordata", "Reptilia", "Crocodylidae", "C. niloticus", "Crocodylus niloticus", Animals.StatusType.ALIVE,
                Animals.MigrationsType.NO_MIGRATING, "It has short legs located on the sides of the body, scaly skin covered with rows of bone plates, a long strong tail and powerful jaws.",
                "Inactive animals. More than half of the crocodiles observed in nature are basking on the shore or in shallow water from early morning until dusk, keeping their jaws open to avoid overheating.",
                "{\"mother\": 1, \"father\": 2, \"child\": []}", "images/croc3.png"
        ));
        animalsDAO.saveCollection(animalsList);

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

        labelsList.add(new Labels(
                1L, animalsDAO.getById(1L), new Date(115, Calendar.OCTOBER, 4), new Date(120, Calendar.JANUARY, 6), employeesDAO.getById(1L)
        ));
        labelsList.add(new Labels(
                2L, animalsDAO.getById(2L), new Date(116, Calendar.MARCH, 4), new Date(121, Calendar.SEPTEMBER, 20), employeesDAO.getById(2L)
        ));
        labelsList.add(new Labels(
                3L, animalsDAO.getById(3L), new Date(119, Calendar.NOVEMBER, 4), null, employeesDAO.getById(3L)
        ));
        labelsDAO.saveCollection(labelsList);
    }

    @BeforeAll
    @AfterEach
    void annihilation() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery("TRUNCATE animals RESTART IDENTITY CASCADE;").executeUpdate();
            session.createSQLQuery("TRUNCATE employees RESTART IDENTITY CASCADE;").executeUpdate();
            session.createSQLQuery("TRUNCATE labels RESTART IDENTITY CASCADE;").executeUpdate();
            session.createSQLQuery("ALTER SEQUENCE animals_id_animal_seq RESTART WITH 1;").executeUpdate();
            session.createSQLQuery("ALTER SEQUENCE employees_id_employee_seq RESTART WITH 1;").executeUpdate();
            session.createSQLQuery("ALTER SEQUENCE labels_id_label_seq RESTART WITH 1;").executeUpdate();
            session.getTransaction().commit();
        }
    }
}