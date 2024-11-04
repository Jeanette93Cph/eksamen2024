package dat.config;
import dat.security.entities.Role;
import dat.security.entities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class Populate
{
    public static void main(String[] args)
    {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();

        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();

            Role adminRole = new Role("ADMIN");
            Role userRole = new Role("USER");

            em.persist(adminRole);
            em.persist(userRole);

            User u1 = new User("john", "password123");
            User u2 = new User("Gitte", "gitte123");
            User u3 = new User("Finn", "finn123");
            User u4 = new User("Lars", "lars123");

            u1.addRole(userRole);
            u2.addRole(userRole);
            u3.addRole(userRole);
            u4.addRole(adminRole);

            em.persist(u1);
            em.persist(u2);
            em.persist(u3);
            em.persist(u4);

            em.getTransaction().commit();
        }
    }


//    private static List<T> getData(T t)
//    {
//
//    }
//
//
//    private static List<T> getAnotherData(T t)
//    {
//
//    }
//
//    // used in Mock
//    public static List<T> populateData()
//    {
//
//
//    }


}


