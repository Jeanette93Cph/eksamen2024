package dat.daos;

import dat.config.Populate;
import dat.dtos.GuideDTO;
import dat.dtos.TripDTO;
import dat.entities.Category;
import dat.entities.Guide;
import dat.entities.Trip;
import dat.security.exceptions.ApiException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class TripDAO implements IDAO<TripDTO, Integer>, ITripGuideDAO
{
    private static TripDAO instance;
    private static EntityManagerFactory emf;

    //singleton pattern. only one instance of the class can be created
    public static TripDAO getInstance(EntityManagerFactory _emf)
    {
        if (instance == null)
        {
            emf = _emf;
            instance = new TripDAO();
        }
        return instance;
    }

    @Override
    public TripDTO create(TripDTO tripDTO)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            Trip trip = new Trip(tripDTO);
            em.persist(trip);
            em.getTransaction().commit();
            return new TripDTO(trip);
        }
    }

    @Override
    public TripDTO getById(Integer id) throws ApiException
    {
        try (EntityManager em = emf.createEntityManager())
        {
            Trip trip = em.find(Trip.class, id);
            if (trip == null)
            {
                throw new ApiException(404, "Trip not found");
            }
            return new TripDTO(trip);
        }
    }

    @Override
    public List<TripDTO> getAll()
    {
        try (EntityManager em = emf.createEntityManager())
        {
            TypedQuery<TripDTO> query = em.createQuery("SELECT new dat.dtos.TripDTO(t) FROM Trip t", TripDTO.class);
            return query.getResultList();
        }
    }

    @Override
    public TripDTO update(Integer id, TripDTO tripDTO)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            Trip trip = em.find(Trip.class, id);

            trip.setName(tripDTO.getName());
            trip.setCategory(tripDTO.getCategory());
            trip.setLatitude(tripDTO.getLatitude());
            trip.setLongitude(tripDTO.getLongitude());
            trip.setPrice(tripDTO.getPrice());
            trip.setEndTime(tripDTO.getEndTime());
            trip.setStartTime(tripDTO.getStartTime());

            em.merge(trip);
            em.getTransaction().commit();
            return new TripDTO(trip);
        }
    }

    @Override
    public void delete(Integer id) throws ApiException
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            Trip trip = em.find(Trip.class, id);
            if (trip == null)
            {
                throw new ApiException(404, "Trip not found");
            }
            if (trip != null && trip.getGuide() != null)
            {
                trip.getGuide().getTrips().remove(trip);
                trip.setGuide(null);
            }
            em.remove(trip);
            em.getTransaction().commit();
        }
    }

    @Override
    public void addGuideToTrip(int tripId, int guideId)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            Trip trip = em.find(Trip.class, tripId);
            Guide guide = em.find(Guide.class, guideId);
            trip.setGuide(guide);
            em.getTransaction().commit();
        }
    }

    @Override
    public Set<TripDTO> getTripsByGuide(int guideId)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            Guide guide = em.find(Guide.class, guideId);
            TypedQuery<TripDTO> query = em.createQuery("SELECT new dat.dtos.TripDTO(t) FROM Trip t WHERE t.guide.id =:guideId", TripDTO.class);
            query.setParameter("guideId", guideId);

            return query.getResultList().stream().collect(Collectors.toSet());
        }
    }



    public List<TripDTO> getByType(Category category)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            TypedQuery<TripDTO> query = em.createQuery("SELECT new dat.dtos.TripDTO(t) FROM Trip t WHERE t.category = :category", TripDTO.class);
            return query.setParameter("category",category).getResultList();
        }
    }




    public boolean validatePrimaryKey(Integer integer) {
        try (EntityManager em = emf.createEntityManager()) {
            Trip trip = em.find(Trip.class, integer);
            return trip != null;
        }
    }

    //find a way to get an overview with each guide, and the total sum price of all trips offered by each guide.
    public List<GuideDTO> getGuideTotalPrices()
    {
        try (EntityManager em = emf.createEntityManager())
        {
            List<Guide> guides = em.createQuery("SELECT g FROM Guide g JOIN FETCH g.trips", Guide.class).getResultList();

            return guides.stream().map(guide ->
            {
                double totalPrice = guide.getTrips().stream()
                        .mapToDouble(Trip::getPrice)
                        .sum();

                return new GuideDTO(guide.getId(), totalPrice);
            }).collect(Collectors.toList());
        }
    }


}
