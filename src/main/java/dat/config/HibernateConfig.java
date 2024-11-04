package dat.config;

import dat.security.entities.Role;
import dat.security.entities.User;
import dat.utils.Utils;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.util.Properties;

//handles both the connection to the database and the configuration of how data should be managed and stored
public class HibernateConfig
{

    //the EntityManagerFactory creates EntityManager instances for database interactions, including CRUD operations and query execution.
    private static EntityManagerFactory emf;
    private static EntityManagerFactory emfTest;

    //variable that indicates whether the application is in test mode.
    //initialized to false, meaning not in test mode by default.
    private static Boolean isTest = false;


    //sets the test mode status for the application
    public static void setTest(Boolean test)
    {
        isTest = test;
    }

    //returns value of isTest. used in getEntityManagerFactory() and getEntityManagerFactoryForTest()
    public static Boolean getTest()
    {
        return isTest;
    }

    //retrieves existing EntityManagerFactory, and creating it if it doesn't exist.
    public static EntityManagerFactory getEntityManagerFactory()
    {
        if (emf == null)
            emf = createEMF(getTest());
        return emf;
    }

    //retrieves the test-specific EntityManagerFactory, creating it if it doesn’t exist, and sets the application to test mode.
    public static EntityManagerFactory getEntityManagerFactoryForTest()
    {
        if (emfTest == null)
        {
            setTest(true);
            emfTest = createEMF(getTest());
        }
        return emfTest;
    }

    // TODO: IMPORTANT: Add Entity classes here for them to be registered with Hibernate
    // this registration allows hibernate to recognize and manage these entities when performing database operations.
    private static void getAnnotationConfiguration(Configuration configuration)
    {
        configuration.addAnnotatedClass(User.class);
        configuration.addAnnotatedClass(Role.class);
    }

    //constructs and returns a new EntityManagerFactory based on the specified environment(test, deployed or development)
    private static EntityManagerFactory createEMF(boolean forTest)
    {
        try
        {
            Configuration configuration = new Configuration();
            Properties props = new Properties();
            // Set the properties
            setBaseProperties(props);
            if (forTest)
            {
                props = setTestProperties(props);
            } else if (System.getenv("DEPLOYED") != null)
            {
                setDeployedProperties(props);
            } else
            {
                props = setDevProperties(props);
            }
            configuration.setProperties(props);
            getAnnotationConfiguration(configuration);

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties())
                    .build();
            SessionFactory sf = configuration.buildSessionFactory(serviceRegistry);
            EntityManagerFactory emf = sf.unwrap(EntityManagerFactory.class);
            return emf;
        } catch (Throwable ex)
        {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    //sets base properties that are necessary for establishing a connection to the database.
    //always used regardless of the environment.
    private static Properties setBaseProperties(Properties props)
    {
        props.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        props.put("hibernate.connection.driver_class", "org.postgresql.Driver");
        props.put("hibernate.hbm2ddl.auto", "update");
        props.put("hibernate.current_session_context_class", "thread");
        props.put("hibernate.show_sql", "true");
        props.put("hibernate.format_sql", "true");
        props.put("hibernate.use_sql_comments", "true");
        return props;
    }

    //used only when deploying the project to the cloud.
    //sets properties for a deployed environment by retrieving database connection details from environment variables.
    private static Properties setDeployedProperties(Properties props)
    {
        String DBName = System.getenv("DB_NAME");
        props.setProperty("hibernate.connection.url", System.getenv("CONNECTION_STR") + DBName);
        props.setProperty("hibernate.connection.username", System.getenv("DB_USERNAME"));
        props.setProperty("hibernate.connection.password", System.getenv("DB_PASSWORD"));
        return props;
    }

    //used for local development, not in deployment.
    //sets properties for a development enviroment. establishing a connection to a local PostgreSQL database, allowing developers to run and test the application locally.
    private static Properties setDevProperties(Properties props)
    {
        String DBName = Utils.getPropertyValue("DB_NAME", "config.properties");
        props.put("hibernate.connection.url", "jdbc:postgresql://localhost:5432/" + DBName);
        props.put("hibernate.connection.username", "postgres");
        props.put("hibernate.connection.password", "postgres");
        return props;
    }

    //used only for running tests.
    //sets properties for a testing environment. using a Testcontainers JDBC driver to connect to a test database. It ensures the database is recreated for each test run.
    private static Properties setTestProperties(Properties props)
    {
        //props.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        props.put("hibernate.connection.driver_class", "org.testcontainers.jdbc.ContainerDatabaseDriver");
        props.put("hibernate.connection.url", "jdbc:tc:postgresql:15.3-alpine3.18:///test_db");
        props.put("hibernate.connection.username", "postgres");
        props.put("hibernate.connection.password", "postgres");
        props.put("hibernate.archive.autodetection", "class");
        props.put("hibernate.show_sql", "true");
        props.put("hibernate.hbm2ddl.auto", "create-drop"); // update for production
        return props;
    }
}
