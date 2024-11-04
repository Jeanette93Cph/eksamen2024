package dat.config;
import dat.entities.Category;
import dat.entities.Guide;
import dat.entities.Trip;
import dat.security.entities.Role;
import dat.security.entities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Populate
{
    public static void main(String[] args)
        {
            EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();

            try (EntityManager em = emf.createEntityManager())
            {
                    em.getTransaction().begin();

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

                    em.persist(guide1);
                    em.persist(guide2);
                    em.persist(guide3);
                    em.persist(guide4);
                    em.persist(guide5);
                    em.persist(guide6);

                    trip1.setGuide(guide1);
                    trip2.setGuide(guide2);
                    trip3.setGuide(guide3);
                    trip4.setGuide(guide4);
                    trip5.setGuide(guide5);
                    trip6.setGuide(guide6);

                    em.persist(trip1);
                    em.persist(trip2);
                    em.persist(trip3);
                    em.persist(trip4);
                    em.persist(trip5);
                    em.persist(trip6);

                    em.getTransaction().commit();
            }

        }
}


