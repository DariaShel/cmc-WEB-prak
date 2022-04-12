package ru.msu.cmc.webprak.DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import ru.msu.cmc.webprak.models.Animals;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(locations="classpath:application.properties")
public class AnimalsDAOTest {

    @Autowired
    private AnimalsDAO animalsDAO;
    @Autowired
    private SessionFactory sessionFactory;

    @Test
    void testSimpleManipulations() {
        List<Animals> animalsListAll = (List<Animals>) animalsDAO.getAll();
        assertEquals(3, animalsListAll.size());

        List<Animals> natQuery = animalsDAO.getAnimalsByName("Natasha");
        assertEquals(1, natQuery.size());
        assertEquals("Natasha", natQuery.get(0).getName());


        Animals animalsNotExist = animalsDAO.getById(100L);
        assertNull(animalsNotExist);
    }

    @Test
    void testUpdate() {
        String type = "Olya", class_ = "bird";

        Animals updateAnimals = animalsDAO.getSingleAnimalByName("Clint");
        updateAnimals.setType(type);
        updateAnimals.setClass_field(class_);
        animalsDAO.update(updateAnimals);

        Animals clint = animalsDAO.getSingleAnimalByName("Clint");
        assertEquals(type, clint.getType());
        assertEquals(class_, clint.getClass_field());
    }

    @Test
    void testDelete() {
        Animals deleteAnimals = animalsDAO.getSingleAnimalByName("Gena");
        animalsDAO.delete(deleteAnimals);

        Animals gena = animalsDAO.getSingleAnimalByName("Gena");
        assertNull(gena);
    }

    @BeforeEach
    void beforeEach() {
        List<Animals> animalsList = new ArrayList<>();
        animalsList.add(new Animals(
                                    0L, "Natasha", "Chordata", "Reptilia", "Crocodylidae", "C. niloticus", "Crocodylus niloticus", Animals.StatusType.ALIVE,
                                    Animals.MigrationsType.NO_MIGRATING, "It has short legs located on the sides of the body, scaly skin covered with rows of bone plates, a long strong tail and powerful jaws.",
                                    "Inactive animals. More than half of the crocodiles observed in nature are basking on the shore or in shallow water from early morning until dusk, keeping their jaws open to avoid overheating.",
                                    "{\"mother\": -1, \"father\": -1, \"child\": [3]}", "images/croc1.jpg"
                                    ));
        animalsList.add(new Animals(
                                    1L, "Gena", "Chordata", "Reptilia", "Crocodylidae", "C. niloticus", "Crocodylus niloticus", Animals.StatusType.ALIVE,
                                    Animals.MigrationsType.NO_MIGRATING, "It has short legs located on the sides of the body, scaly skin covered with rows of bone plates, a long strong tail and powerful jaws.",
                                    "Inactive animals. More than half of the crocodiles observed in nature are basking on the shore or in shallow water from early morning until dusk, keeping their jaws open to avoid overheating.",
                                    "{\"mother\": -1, \"father\": -1, \"child\": [3]}", "images/croc2.jpg"
                                    ));
        animalsList.add(new Animals(
                                    2L, "Clint", "Chordata", "Reptilia", "Crocodylidae", "C. niloticus", "Crocodylus niloticus", Animals.StatusType.ALIVE,
                                    Animals.MigrationsType.NO_MIGRATING, "It has short legs located on the sides of the body, scaly skin covered with rows of bone plates, a long strong tail and powerful jaws.",
                                    "Inactive animals. More than half of the crocodiles observed in nature are basking on the shore or in shallow water from early morning until dusk, keeping their jaws open to avoid overheating.",
                                    "{\"mother\": 1, \"father\": 2, \"child\": []}", "images/croc3.png"
                                    ));
        
        animalsDAO.saveCollection(animalsList);
    }

    @BeforeAll
    @AfterEach
    void annihilation() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery("TRUNCATE animals RESTART IDENTITY CASCADE;").executeUpdate();
            session.createSQLQuery("ALTER SEQUENCE animals_id_animal_seq RESTART WITH 1;").executeUpdate();
            session.getTransaction().commit();
        }
    }
}