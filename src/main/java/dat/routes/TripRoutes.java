package dat.routes;

import dat.controllers.TripController;
import dat.security.enums.Role;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

public class TripRoutes
{
    private static final TripController tripController = new TripController();

    public EndpointGroup getTripRoutes()
    {
        return () ->
        {
            get("/", tripController::getAll, Role.USER, Role.ADMIN);
            get("/{id}", tripController::getById, Role.USER, Role.ADMIN);
            post("/", tripController::create, Role.ADMIN);
            put("/{id}", tripController::update, Role.ADMIN);
            delete("/{id}", tripController::delete, Role.ADMIN);
            put("/{tripId}/guides/{guideId}", tripController::addGuideToTrip, Role.ADMIN);
            get("/{guideId}/trips", tripController::getTripsByGuide, Role.USER, Role.ADMIN);
            get("/type/{type}", tripController::getByType, Role.USER, Role.ADMIN);
            get("/guides/totalprice", tripController::getGuideTotalPrices, Role.USER, Role.ADMIN);
        };
    }
}
