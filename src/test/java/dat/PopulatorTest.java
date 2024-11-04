package dat;

import dat.security.entities.Role;
import dat.security.entities.User;
import dk.bugelhartmann.UserDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.time.LocalDateTime;
import java.util.HashSet;
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


//    public static List<T> populateData(EntityManagerFactory emf)
//    {
//
//    }
//
//    private static List<T> getData(T t)
//    {
//
//    }
//
//    private static List<T> getAnotherData(T t)
//    {
//
//    }


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

