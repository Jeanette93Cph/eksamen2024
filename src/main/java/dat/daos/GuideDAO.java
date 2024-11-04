package dat.daos;

import dat.dtos.GuideDTO;
import dat.entities.Guide;
import dat.security.exceptions.ApiException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class GuideDAO implements IDAO<GuideDTO, Integer>
{
    private static GuideDAO instance;
    private static EntityManagerFactory emf;

    //singleton pattern. only one instance of the class can be created
    public static GuideDAO getInstance(EntityManagerFactory _emf)
    {
        if (instance == null)
        {
            emf = _emf;
            instance = new GuideDAO();
        }
        return instance;
    }


    @Override
    public GuideDTO create(GuideDTO guideDTO)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            Guide guide = new Guide(guideDTO);
            em.persist(guide);
            em.getTransaction().commit();
            return new GuideDTO(guide);
        }
    }

    @Override
    public GuideDTO getById(Integer id) throws ApiException
    {
        try (EntityManager em = emf.createEntityManager())
        {
            Guide guide = em.find(Guide.class, id);
            if (guide == null)
            {
                throw new ApiException(404, "Event not found");
            }
            return new GuideDTO(guide);
        }
    }

    @Override
    public List<GuideDTO> getAll()
    {
        try (EntityManager em = emf.createEntityManager())
        {
            TypedQuery<GuideDTO> query = em.createQuery("SELECT new dat.dtos.GuideDTO(g) FROM Guide g", GuideDTO.class);
            return query.getResultList();
        }
    }

    @Override
    public GuideDTO update(Integer id, GuideDTO guideDTO)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            Guide guide = em.find(Guide.class, id);

            guide.setFirstName(guideDTO.getFirstName());
            guide.setLastName(guideDTO.getLastName());
            guide.setPhone(guideDTO.getPhone());
            guide.setEmail(guideDTO.getEmail());
            guide.setYearsOfExperience(guideDTO.getYearsOfExperience());

            em.merge(guide);
            em.getTransaction().commit();
            return new GuideDTO(guide);
        }
    }

    @Override
    public void delete(Integer id)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            Guide guide = em.find(Guide.class, id);
            em.remove(guide);
            em.getTransaction().commit();
        }
    }

}
