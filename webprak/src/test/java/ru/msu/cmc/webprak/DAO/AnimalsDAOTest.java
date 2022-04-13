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

        Animals animalsNotExist = animalsDAO.getById(100L);
        assertNull(animalsNotExist);

        List<Animals> nameQuery = animalsDAO.getAnimalsByName("Natasha");
        assertEquals(1, nameQuery.size());
        assertEquals("Natasha", nameQuery.get(0).getName());

        List<Animals> specQuery = animalsDAO.getAnimalsBySpecies("C. niloticus");
        assertEquals(3, specQuery.size());
        assertEquals("C. niloticus", specQuery.get(0).getSpecies());

        List<Animals> latQuery = animalsDAO.getAnimalsByLatinName("Crocodylus niloticus");
        assertEquals(3, latQuery.size());
        assertEquals("Crocodylus niloticus", specQuery.get(0).getLatin_name());
    }

    @Test
    void testUpdate() {
        String type = "cat", class_ = "cat", name = "Murka", family = "cat", species = "home cat", latin_name = "Cat";
        String appearance = "beauty", behaviour = "good", photo = "cat.jpg", communications = "other cats";

        Animals updateAnimals = animalsDAO.getById(1L);
        updateAnimals.setName(name);
        updateAnimals.setType(type);
        updateAnimals.setClass_field(class_);
        updateAnimals.setFamily(family);
        updateAnimals.setSpecies(species);
        updateAnimals.setLatin_name(latin_name);
        updateAnimals.setStatus(Animals.StatusType.ALIVE);
        updateAnimals.setMigrations(Animals.MigrationsType.NO_MIGRATING);
        updateAnimals.setAppearance(appearance);
        updateAnimals.setBehavior(behaviour);
        updateAnimals.setCommunications(communications);
        updateAnimals.setPhoto(photo);
        animalsDAO.update(updateAnimals);

        Animals test = animalsDAO.getById(1L);
        assertEquals(name, test.getName());
        assertEquals(type, test.getType());
        assertEquals(class_, test.getClass_field());
        assertEquals(family, test.getFamily());
        assertEquals(species, test.getSpecies());
        assertEquals(latin_name, test.getLatin_name());
        assertEquals(Animals.StatusType.ALIVE, test.getStatus());
        assertEquals(Animals.MigrationsType.NO_MIGRATING, test.getMigrations());
        assertEquals(appearance, test.getAppearance());
        assertEquals(behaviour, test.getBehavior());
        assertEquals(communications, test.getCommunications());
        assertEquals(photo, test.getPhoto());
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