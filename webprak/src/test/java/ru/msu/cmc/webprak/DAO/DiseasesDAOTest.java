package ru.msu.cmc.webprak.DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import ru.msu.cmc.webprak.models.Animals;
import ru.msu.cmc.webprak.models.Diseases;
import ru.msu.cmc.webprak.models.Employees;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(locations="classpath:application.properties")
public class DiseasesDAOTest {

    @Autowired
    private DiseasesDAO diseasesDAO;
    @Autowired
    private AnimalsDAO animalsDAO;
    @Autowired
    private EmployeesDAO employeesDAO;
    @Autowired
    private SessionFactory sessionFactory;

    @Test
    void testSimpleManipulations() {
        List<Diseases> diseasesListAll = (List<Diseases>) diseasesDAO.getAll();
        assertEquals(5, diseasesListAll.size());

        Diseases diseasesNotExist = diseasesDAO.getById(100L);
        assertNull(diseasesNotExist);

        List<Diseases> diseaseQuery = diseasesDAO.getDiseaseByDiseaseName("stomatitis");
        assertEquals(3, diseaseQuery.size());
        assertEquals("stomatitis", diseaseQuery.get(0).getName_disease());
    }

    @Test
    void testUpdate() {
        Animals idAnimal = animalsDAO.getById(1L);
        Employees idEmployee = employeesDAO.getById(1L);
        String name_disease = "headache", help = "some help", consequenses = "some consequenses";
        Date recovery = new Date(120, Calendar.NOVEMBER, 4);
        Date time_disease = new Date(120, Calendar.SEPTEMBER, 10);

        Diseases updateDiseases = diseasesDAO.getById(2L);
        updateDiseases.setId_animal(idAnimal);
        updateDiseases.setName_disease(name_disease);
        updateDiseases.setTime_disease(time_disease);
        updateDiseases.setRecovery(recovery);
        updateDiseases.setId_employee(idEmployee);
        updateDiseases.setHelp(help);
        updateDiseases.setConsequences(consequenses);
        diseasesDAO.update(updateDiseases);

        Diseases test = diseasesDAO.getById(2L);
        assertEquals(idAnimal.getId_animal(), test.getId_animal().getId_animal());
        assertEquals(name_disease, test.getName_disease());
        assertEquals(time_disease, test.getTime_disease());
        assertEquals(recovery, test.getRecovery());
        assertEquals(idEmployee.getId_employee(), test.getId_employee().getId_employee());
        assertEquals(help, test.getHelp());
        assertEquals(consequenses, test.getConsequences());
    }

    @Test
    void testDelete() {
        Diseases deleteDiseases = diseasesDAO.getById(2L);
        diseasesDAO.delete(deleteDiseases);

        Diseases disease = diseasesDAO.getById(2L);
        assertNull(disease);
    }

    @BeforeEach
    void beforeEach() {
        List<Animals> animalsList = new ArrayList<>();
        List<Employees> employeesList = new ArrayList<>();
        List<Diseases> diseasesList = new ArrayList<>();
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

        diseasesList.add(new Diseases(
                1L, animalsDAO.getById(1L), "stomatitis", new Date(120, Calendar.NOVEMBER, 4), new Date(120, Calendar.DECEMBER, 1), employeesDAO.getById(1L), "Local oral treatment and general antibacterial therapy",
                "None"
        ));
        diseasesList.add(new Diseases(
                2L, animalsDAO.getById(1L), "pneumonia", new Date(119, Calendar.JANUARY, 4), new Date(119, Calendar.JANUARY, 25), employeesDAO.getById(3L), "General use of broad-spectrum injectable antibiotics",
                "Wheezing when breathing"
        ));
        diseasesList.add(new Diseases(
                3L, animalsDAO.getById(2L), "stomatitis", new Date(121, Calendar.JULY, 4), new Date(120, Calendar.AUGUST, 2), employeesDAO.getById(2L), "Local oral treatment and general antibacterial therapy",
                "None"
        ));
        diseasesList.add(new Diseases(
                4L, animalsDAO.getById(1L), "pneumonia", new Date(117, Calendar.OCTOBER, 4), new Date(120, Calendar.NOVEMBER, 4), employeesDAO.getById(3L), "General use of broad-spectrum injectable antibiotics",
                "Wheezing when breathing"
        ));
        diseasesList.add(new Diseases(
                5L, animalsDAO.getById(2L), "stomatitis", new Date(120, Calendar.APRIL, 24), new Date(120, Calendar.MAY, 4), employeesDAO.getById(3L), "Local oral treatment and general antibacterial therapy",
                "None"
        ));
        diseasesDAO.saveCollection(diseasesList);
    }

    @BeforeAll
    @AfterEach
    void annihilation() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery("TRUNCATE animals RESTART IDENTITY CASCADE;").executeUpdate();
            session.createSQLQuery("TRUNCATE employees RESTART IDENTITY CASCADE;").executeUpdate();
            session.createSQLQuery("TRUNCATE diseases RESTART IDENTITY CASCADE;").executeUpdate();
            session.createSQLQuery("ALTER SEQUENCE animals_id_animal_seq RESTART WITH 1;").executeUpdate();
            session.createSQLQuery("ALTER SEQUENCE employees_id_employee_seq RESTART WITH 1;").executeUpdate();
            session.createSQLQuery("ALTER SEQUENCE diseases_id_disease_seq RESTART WITH 1;").executeUpdate();
            session.getTransaction().commit();
        }
    }
}