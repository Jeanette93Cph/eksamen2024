package dat.config;
import dat.routes.Routes;
import dat.security.controllers.AccessController;
import dat.security.enums.Role;
import dat.security.exceptions.ApiException;
import dat.security.routes.SecurityRoutes;
import dat.utils.Utils;
import io.javalin.Javalin;
import io.javalin.config.JavalinConfig;
import io.javalin.http.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ApplicationConfig
{
    private static Routes routes = new Routes();
    private static AccessController accessController = new AccessController();
    private static Logger logger = LoggerFactory.getLogger(ApplicationConfig.class);

    //sets up the javalin application by determining the presentation and organization of the API routes.
    public static void configuration(JavalinConfig config)
    {
        config.showJavalinBanner = false;
        config.bundledPlugins.enableRouteOverview("/routes", Role.ANYONE);
        config.router.contextPath = "/api"; // base path for all endpoints
        config.router.apiBuilder(routes.getApiRoutes());
        config.router.apiBuilder(SecurityRoutes.getSecuredRoutes());
        config.router.apiBuilder(SecurityRoutes.getSecurityRoutes());
    }


    public static Javalin startServer(int port)
    {
        //creates a new javalin instance with the above configuration.
        Javalin app = Javalin.create(ApplicationConfig::configuration);

        //check user permissions before javalin decides which route to use.
        app.beforeMatched(accessController::accessHandler);

        //addtional check for permissions before matching routes
        app.beforeMatched(ctx -> accessController.accessHandler(ctx));

        //make sure that Javalin handles any error of the type Exception with generalExceptionHandler
        app.exception(Exception.class, ApplicationConfig::generalExceptionHandler);

        //make sure that Javalin handles any error of the type ApiException with apiExceptionHandler
        app.exception(ApiException.class, ApplicationConfig::apiExceptionHandler);

        app.start(port);

        //returning app, so we can control the server
        return app;
    }

    public static void stopServer(Javalin app)
    {
        app.stop();
    }

    //logs and sends a JSON response with the error message
    private static void generalExceptionHandler(Exception e, Context ctx)
    {
        logger.error("An unhandled exception occurred", e.getMessage());
        ctx.json(Utils.convertToJsonMessage(ctx, "message", e.getMessage()));
    }

    //logs error message and statuscode and sends a JSON response with the error message
    public static void apiExceptionHandler(ApiException e, Context ctx) {
        ctx.status(e.getCode());
        logger.warn("An API exception occurred: Code: {}, Message: {}", e.getCode(), e.getMessage());
        ctx.json(Utils.convertToJsonMessage(ctx, "warning", e.getMessage()));
    }
}
