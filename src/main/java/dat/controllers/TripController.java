package dat.controllers;

import dat.config.HibernateConfig;
import dat.config.Populate;
import dat.daos.TripDAO;
import dat.dtos.GuideDTO;
import dat.dtos.TripDTO;
import dat.entities.Category;
import dat.entities.Guide;
import dat.entities.Trip;
import dat.security.exceptions.ApiException;
import io.javalin.http.Context;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TripController
{
    private final TripDAO dao;

    //dao is being connected to the database through emf
    public TripController()
    {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        this.dao = TripDAO.getInstance(emf);
    }


    public void create(Context ctx)
    {
        TripDTO jsonRequest = ctx.bodyAsClass(TripDTO.class);
        TripDTO tripDTO = dao.create(jsonRequest);
        ctx.res().setStatus(201);
        ctx.json(tripDTO, TripDTO.class);
    }


    public void getById(Context ctx) throws ApiException
    {
        try
        {
            Integer id = ctx.pathParamAsClass("id", Integer.class).check(this::validatePrimaryKey, "Not a valid id").get();
            TripDTO tripDTO = dao.getById(id);
            ctx.res().setStatus(200);
            ctx.json(tripDTO, TripDTO.class);
        } catch (Exception e)
        {
            throw new ApiException(400, "Missing required parameter: id");
        }
    }


    public void getAll(Context ctx)
    {
        List<TripDTO> tripDTOS = dao.getAll();
        ctx.res().setStatus(200);
        ctx.json(tripDTOS, TripDTO.class);

    }


    public void update(Context ctx)
    {
        Integer id = ctx.pathParamAsClass("id", Integer.class).check(this::validatePrimaryKey, "Not a valid id").get();

        TripDTO jsonRequest = ctx.bodyAsClass(TripDTO.class);
        TripDTO tripDTO = dao.update(id, jsonRequest);

        ctx.res().setStatus(200);
        ctx.json(tripDTO, TripDTO.class);
    }


    public void delete(Context ctx) throws ApiException
    {
        try
        {
            Integer id = ctx.pathParamAsClass("id", Integer.class).check(this::validatePrimaryKey, "Not a valid id").get();
            dao.delete(id);
            ctx.res().setStatus(204);
        } catch (Exception e)
        {
            throw new ApiException(400, "Missing required parameter: id");
        }
    }


    public void addGuideToTrip(Context ctx)
    {
        Integer tripId = ctx.pathParamAsClass("tripId", Integer.class).check(this::validatePrimaryKey, "Not a valid id").get();
        int guideId = ctx.pathParamAsClass("guideId", Integer.class).check(this::validatePrimaryKey, "Not a valid id").get();
        dao.addGuideToTrip(tripId, guideId);
        ctx.res().setStatus(200);
    }


    public void getTripsByGuide(Context ctx)
    {
        Integer guideId = ctx.pathParamAsClass("guideId", Integer.class).check(this::validatePrimaryKey, "Not a valid id").get();
        Set<TripDTO> tripDTOS = dao.getTripsByGuide(guideId);
        ctx.res().setStatus(200);
        ctx.json(tripDTOS, TripDTO.class);
    }


    public void getByType(Context ctx)
    {
        Category category = Category.valueOf(ctx.pathParam("type"));

        // DTO
        List<TripDTO> list = dao.getByType(category);
        // response
        ctx.res().setStatus(200);
        ctx.json(list, TripDTO.class);
    }


    public void getGuideTotalPrices(Context ctx)
    {
        List<GuideDTO> guideDTOs = dao.getGuideTotalPrices();
        ctx.res().setStatus(200);
        ctx.json(guideDTOs, TripDTO.class);
    }


    public boolean validatePrimaryKey(Integer integer)
    {
        return dao.validatePrimaryKey(integer);
    }
}
