package dat;

import dat.entities.Category;
import dat.entities.Guide;
import dat.entities.Trip;
import dat.security.entities.Role;
import dat.security.entities.User;
import dk.bugelhartmann.UserDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PopulatorTest
{

    public static Set<UserDTO> populateUsers(EntityManagerFactory emf)
    {
        Set<UserDTO> userSet = new HashSet<>();

        Role adminRole = new Role("ADMIN");
        Role userRole = new Role("USER");

        User u1 = new User("john", "password123");
        User u2 = new User("Gitte", "gitte123");
        User u3 = new User("Finn", "finn123");
        User u4 = new User("Lars", "lars123");

        u1.addRole(userRole);
        u2.addRole(userRole);
        u3.addRole(userRole);
        u4.addRole(adminRole);

        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            em.persist(adminRole);
            em.persist(userRole);
            em.persist(u1);
            em.persist(u2);
            em.persist(u3);
            em.persist(u4);
            em.getTransaction().commit();
        }

        UserDTO userDTO1 = new UserDTO(u1.getUsername(), "password123");
        UserDTO userDTO2 = new UserDTO(u2.getUsername(), "gitte123");
        UserDTO userDTO3 = new UserDTO(u3.getUsername(), "finn123");
        UserDTO userDTO4 = new UserDTO(u4.getUsername(), "lars123");

        userSet.add(userDTO1);
        userSet.add(userDTO2);
        userSet.add(userDTO3);
        userSet.add(userDTO4);

        return userSet;
    }


    public static List<Trip> populateData(EntityManagerFactory emf)
    {
        List<Trip> list = new ArrayList<>();

        Trip trip1 = new Trip("Mountain Hike", LocalDateTime.of(2023, 5, 12, 9, 0), LocalDateTime.of(2023, 5, 12, 17, 0), 34.0564, -118.2468, 99.99, Category.BEACH);
        Trip trip2 = new Trip("City Tour", LocalDateTime.of(2023, 6, 15, 10, 0), LocalDateTime.of(2023, 6, 15, 13, 0), 40.7128, -74.0060, 49.99, Category.CITY);
        Trip trip3 = new Trip("Beach Day", LocalDateTime.of(2023, 7, 20, 8, 0), LocalDateTime.of(2023, 7, 20, 18, 0), 36.7783, -119.4179, 59.99, Category.FOREST);
        Trip trip4 = new Trip("Wine Tasting", LocalDateTime.of(2023, 8, 18, 11, 0), LocalDateTime.of(2023, 8, 18, 15, 0), 34.0522, -118.2437, 129.99, Category.LAKE);
        Trip trip5 = new Trip("Desert Safari", LocalDateTime.of(2023, 9, 25, 16, 0), LocalDateTime.of(2023, 9, 25, 20, 0), 25.276987, 55.296249, 149.99, Category.SEA);
        Trip trip6 = new Trip("Historical Walk", LocalDateTime.of(2023, 10, 30, 14, 0), LocalDateTime.of(2023, 10, 30, 17, 0), 51.5074, -0.1278, 39.99, Category.SNOW);

        Guide guide1 = new Guide("John", "Doe", "johndoe@example.com", "+123456789", 5);
        Guide guide2 = new Guide("Jane", "Smith", "janesmith@example.com", "+987654321", 8);
        Guide guide3 = new Guide("Michael", "Brown", "michaelbrown@example.com", "+456123789", 10);
        Guide guide4 = new Guide("Emily", "Davis", "emilydavis@example.com", "+321654987", 3);
        Guide guide5 = new Guide("Daniel", "Johnson", "danieljohnson@example.com", "+654789321", 7);
        Guide guide6 = new Guide("Sophia", "Martinez", "sophiamartinez@example.com", "+789321456", 6);

        trip1.setGuide(guide1);
        trip2.setGuide(guide2);
        trip3.setGuide(guide3);
        trip4.setGuide(guide4);
        trip5.setGuide(guide5);
        trip6.setGuide(guide6);

        list.add(trip1);
        list.add(trip2);
        list.add(trip3);
        list.add(trip4);
        list.add(trip5);
        list.add(trip6);

        return list;
    }



    public static void cleanUpData(EntityManagerFactory emf)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM User").executeUpdate();
            em.createQuery("DELETE FROM Role").executeUpdate();
            em.getTransaction().commit();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}

