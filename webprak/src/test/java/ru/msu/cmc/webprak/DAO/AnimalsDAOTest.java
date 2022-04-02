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
        assertEquals(6, animalsListAll.size());

        List<Animals> natQuery = animalsDAO.getAnimalsByName("Olya");
        assertEquals(1, natQuery.size());
        assertEquals("Natasha", natQuery.get(0).getName());


        Animals animalsNotExist = animalsDAO.getById(100L);
        assertNull(animalsNotExist);
    }

    @Test
    void testUpdate() {
        String type = "Olya", class_ = "bird";

        Animals updateAnimals = (Animals) animalsDAO.getAnimalsByName("Clint");
        updateAnimals.setType(type);
        updateAnimals.setClass_field(class_);
        animalsDAO.update(updateAnimals);

        Animals nat = (Animals) animalsDAO.getAnimalsByName("Clint");
        assertEquals(type, nat.getType());
        assertEquals(class_, nat.getClass_field());
    }

    @Test
    void testDelete() {
        Animals deleteAnimals = (Animals) animalsDAO.getAnimalsByName("Gena");
        animalsDAO.delete(deleteAnimals);

        Animals gena = (Animals) animalsDAO.getAnimalsByName("Gena");
        assertNull(gena);
    }

    @BeforeEach
    void beforeEach() {
        List<Animals> animalsList = new ArrayList<>();
        animalsList.add(new Animals(
                                    "Natasha", "Chordata", "Reptilia", "Crocodylidae", "C. niloticus", "Crocodylus niloticus", 1,
                                    0, "It has short legs located on the sides of the body, scaly skin covered with rows of bone plates, a long strong tail and powerful jaws. The crocodiles eyes are equipped with a third eyelid, which serves for additional protection under water. Near them there are special glands that allow you to rinse your eyes with liquid. The nostrils, ears and eyes are located in the upper part of the head, so that the crocodile can almost completely submerge into the water, leaving them on the surface. The coloring of the Nile crocodile provides him with a disguise. The belly has a yellow tint",
                                    "Inactive animals. More than half of the crocodiles observed in nature are basking on the shore or in shallow water from early morning until dusk, keeping their jaws open to avoid overheating. The abrupt closure of the mouth serves as a threat signal for other crocodiles and other animals. Nile crocodiles are very aggressive towards other animals, but at the same time they tolerate a large number of their relatives nearby. Despite the fact that they can remain practically motionless for several hours, Nile crocodiles are well aware of the surroundings and the presence of other animals nearby due to their developed senses.",
                                    "{\"mother\": -1, \"father\": -1, \"child\": [3]}", "images/croc1.jpg"
                                    ));
        animalsList.add(new Animals(
                                    "Gena", "Chordata", "Reptilia", "Crocodylidae", "C. niloticus", "Crocodylus niloticus", 1,
                                    0, "It has short legs located on the sides of the body, scaly skin covered with rows of bone plates, a long strong tail and powerful jaws. The crocodiles eyes are equipped with a third eyelid, which serves for additional protection under water. Near them there are special glands that allow you to rinse your eyes with liquid. The nostrils, ears and eyes are located in the upper part of the head, so that the crocodile can almost completely submerge into the water, leaving them on the surface. The coloring of the Nile crocodile provides him with a disguise. The belly has a yellow tint",
                                    "Inactive animals. More than half of the crocodiles observed in nature are basking on the shore or in shallow water from early morning until dusk, keeping their jaws open to avoid overheating. The abrupt closure of the mouth serves as a threat signal for other crocodiles and other animals. Nile crocodiles are very aggressive towards other animals, but at the same time they tolerate a large number of their relatives nearby. Despite the fact that they can remain practically motionless for several hours, Nile crocodiles are well aware of the surroundings and the presence of other animals nearby due to their developed senses.",
                                    "{\"mother\": -1, \"father\": -1, \"child\": [3]}", "images/croc2.jpg"
                                    ));
        animalsList.add(new Animals(
                                    "Clint", "Chordata", "Reptilia", "Crocodylidae", "C. niloticus", "Crocodylus niloticus", 1,
                                    0, "It has short legs located on the sides of the body, scaly skin covered with rows of bone plates, a long strong tail and powerful jaws. The crocodiles eyes are equipped with a third eyelid, which serves for additional protection under water. Near them there are special glands that allow you to rinse your eyes with liquid. The nostrils, ears and eyes are located in the upper part of the head, so that the crocodile can almost completely submerge into the water, leaving them on the surface. The coloring of the Nile crocodile provides him with a disguise. The belly has a yellow tint",
                                    "Inactive animals. More than half of the crocodiles observed in nature are basking on the shore or in shallow water from early morning until dusk, keeping their jaws open to avoid overheating. The abrupt closure of the mouth serves as a threat signal for other crocodiles and other animals. Nile crocodiles are very aggressive towards other animals, but at the same time they tolerate a large number of their relatives nearby. Despite the fact that they can remain practically motionless for several hours, Nile crocodiles are well aware of the surroundings and the presence of other animals nearby due to their developed senses.",
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
//            session.createSQLQuery("ALTER SEQUENCE animals_animals_id_seq RESTART WITH 1;").executeUpdate();
            session.getTransaction().commit();
        }
    }
}