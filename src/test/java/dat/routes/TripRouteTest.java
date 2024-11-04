package dat.routes;

import dat.PopulatorTest;
import dat.config.ApplicationConfig;
import dat.config.HibernateConfig;
import dat.dtos.TripDTO;
import dat.entities.Trip;
import dat.security.controllers.SecurityController;
import dat.security.daos.SecurityDAO;
import dat.security.exceptions.ValidationException;
import dk.bugelhartmann.UserDTO;
import io.javalin.Javalin;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TripRouteTest
{

    private static EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryForTest();
    private final static SecurityController securityController = SecurityController.getInstance();
    private static SecurityDAO securityDAO = new SecurityDAO(emf);
    private static Javalin app;
    private static String BASE_URL = "http://localhost:7000/api/trips/";
    private static Set<UserDTO> userSet;
    private static List<UserDTO> userList;
    private static List<TripDTO> triplist;
    private static UserDTO user1, admin;
    private static String userToken, adminToken;


    @BeforeAll
    static void setUpAll()
    {
        HibernateConfig.setTest(true);
        emf = HibernateConfig.getEntityManagerFactoryForTest();
        app = ApplicationConfig.startServer(7000);
    }

    @BeforeEach
    void setUp()
    {
        triplist = PopulatorTest.populateData(emf);
        userSet = PopulatorTest.populateUsers(emf);

        // convert set to list
        userList = new ArrayList<>(userSet);

        user1 = userList.get(0);
        admin = userList.get(1);

        try
        {
            UserDTO verifiedUser1 = securityDAO.getVerifiedUser(user1.getUsername(), user1.getPassword());
            UserDTO verifiedAdmin = securityDAO.getVerifiedUser(admin.getUsername(), admin.getPassword());
            userToken = "Bearer " + securityController.createToken(verifiedUser1);
            adminToken = "Bearer " + securityController.createToken(verifiedAdmin);

        } catch (ValidationException e)
        {
            throw new RuntimeException(e);
        }
    }

    @AfterEach
    void tearDown()
    {
        PopulatorTest.cleanUpData(emf);
    }

    @AfterAll
    static void tearDownAll()
    {
        emf.close();
        ApplicationConfig.stopServer(app);
    }


    @Test
    void getAll()
    {
        List<TripDTO> fetchedEvents =
                given()
                        .when()
                        .header("Authorization", userToken)
                        .get(BASE_URL)
                        .then()
                        .statusCode(200)
                        .body("name", hasItem("City Tour"))
                        .log().all()
                        .extract()
                        .as(new TypeRef<List<TripDTO>>()
                        {});

        assertThat(fetchedEvents.size(), is(6));
    }

}
