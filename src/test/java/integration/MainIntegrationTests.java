package integration;

import api.API;
import api.Util;
import domain.carport.Carport;
import domain.carport.OrderMaterial;
import domain.carport.Shed;
import domain.material.MaterialRepository;
import domain.material.dao.MaterialDAO;
import domain.order.Order;
import domain.order.OrderFactory;
import domain.order.OrderRepository;
import domain.order.dao.OrderDAO;
import domain.order.exceptions.OrderNotFoundException;
import domain.user.User;
import domain.user.UserRepository;
import domain.user.customer.Customer;
import domain.user.dao.UserDAO;
import infrastructure.Database;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import validation.ValidationErrorException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@Tag("integration-test")
public class MainIntegrationTests {

    API api;

    static void resetTestDatabase() {
        String URL = "jdbc:mysql://localhost:3306/?serverTimezone=CET";
        String USER = "fog_test";

        InputStream stream = API.class.getClassLoader().getResourceAsStream("database/restart-test.sql");
        if (stream == null) throw new RuntimeException("restart-test.sql");
        try (Connection conn = DriverManager.getConnection(URL, USER, null)) {
            conn.setAutoCommit(false);
            ScriptRunner runner = new ScriptRunner(conn);
            runner.setLogWriter(null);
            runner.setStopOnError(true);
            runner.runScript(new BufferedReader(new InputStreamReader(stream)));
            conn.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.out.println("Done running restart migration.");
    }

    @BeforeEach
    void setupAPI() throws IOException, SQLException {
        resetTestDatabase();

        String url = "jdbc:mysql://localhost:3306/fog_test?serverTimezone=CET";
        String user = "fog_test";

        Database db = new Database(url, user);
        db.runMigrations();

        MaterialRepository materialRepository = new MaterialDAO(db);
        UserRepository userRepository = new UserDAO(db);
        OrderRepository orderRepository = new OrderDAO(db, userRepository);

        api = new API(materialRepository, orderRepository, userRepository);
    }

    //All main tests expects all input to be correct...

    /*
     *
     * #1 User Story
     *
     * [Som FOG vil jeg gerne have at når en kunde bestiller et tilbud bliver det gemt i en DATABASE]
     *
     * #3
     *
     * [Som KUNDE vil jeg gerne kunne oprette en ordre med min ønskede carports mål.]
     *
     * Test will test an order can be generated and retrieved
     *
     */

    @Test
    void userStory1And3() throws ValidationErrorException, OrderNotFoundException {
        /*Let's create an order...*/
        OrderFactory orderFactory = api.createOrder();
        /*generate our tokens*/
        orderFactory.setToken(Util.generateSecureToken());
        orderFactory.setUuid(UUID.randomUUID());

        /*This order will have no shed...*/
        Shed shed = null;
        orderFactory.setShed(shed);
        /*
        * We're pretending we're getting our data from a form.
        * */
        Carport carport = new Carport(-1, shed, 480, 480, Carport.roofTypes.FLAT, 0, 15);
        orderFactory.setCarport(carport);

        Customer customer = new Customer(-1, "Martin", "Skov", "martinskov@test.dk", "+4520204040", new User.Address("Skovvej 27", "Hillerød", "2500"));
        orderFactory.setCustomer(customer);

        /* No note. */
        orderFactory.setNote(null);

        Order order = orderFactory.validateAndCommit();

        /*Order should now be in database*/
        Order foundOrder = api.getOrder(order.getUuid());
        assertEquals("Martin", order.getCustomer().getFirstname());

        assertEquals(foundOrder, order);
    }

    /*
     *
     * #6 User Story
     *
     * [Som SÆLGER vil jeg gerne kunne have at en stykliste bliver genereret ud fra en ordre, så jeg nemt kan tilpasse et tilbud til en kunde.]
     *
     *
     * Test will generate order and see if list of materials generated from material list > 0
     *
     */
    @Test
    void userStory6() throws ValidationErrorException {
        /*Let's create an order...*/
        OrderFactory orderFactory = api.createOrder();
        /*generate our tokens*/
        orderFactory.setToken(Util.generateSecureToken());
        orderFactory.setUuid(UUID.randomUUID());

        /*This order will have no shed...*/
        Shed shed = null;
        orderFactory.setShed(shed);
        /*
         * We're pretending we're getting our data from a form.
         * */
        Carport carport = new Carport(-1, shed, 480, 480, Carport.roofTypes.FLAT, 0, 15);
        orderFactory.setCarport(carport);

        Customer customer = new Customer(-1, "Martin", "Skov", "martinskov@test.dk", "+4520204040", new User.Address("Skovvej 27", "Hillerød", "2500"));
        orderFactory.setCustomer(customer);

        /* No note. */
        orderFactory.setNote(null);

        Order order = orderFactory.validateAndCommit();

        List<OrderMaterial> listOfMaterials = api.getOrderMaterials(order);
        assertTrue(listOfMaterials.size() > 0);
    }

}
