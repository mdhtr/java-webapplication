package mdhtr.webapplication.integration;

import com.google.common.collect.ImmutableList;
import io.restassured.http.ContentType;
import mdhtr.webapplication.JettyServer;
import mdhtr.webapplication.persistence.H2InMemoryDb;
import mdhtr.webapplication.persistence.Message;
import mdhtr.webapplication.persistence.MessageDao;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class MessageEndpointIntegrationTest {
    private static final int TEST_PORT = 9000;
    private static final String TEST_DB_URL = "jdbc:h2:mem:test";
    private static final String TEST_DB_USER = "";
    private static final String TEST_DB_PASSWORD = "";
    private static String MESSAGE = "test message";
    private static List<Message> MESSAGES = ImmutableList.of(new Message(1, MESSAGE));
    private static String JSON_MESSAGES = "[{\"id\":1,\"message\":\"test message\"}]";

    private MessageDao dao;
    private JettyServer server;

    @BeforeEach
    void setUp() {
        H2InMemoryDb.initInstance(TEST_DB_URL, TEST_DB_USER, TEST_DB_PASSWORD);
        dao = new MessageDao();
        server = new JettyServer(TEST_PORT);
        try {
            server.start();
        } catch (Exception e) {
            throw new RuntimeException("Exception while starting test server", e);
        }
    }

    @AfterEach
    void tearDown() {
        H2InMemoryDb.destroyInstance();
        try {
            server.stop();
        } catch (Exception e) {
            throw new RuntimeException("Exception while stopping test server", e);
        }
    }

    @Test
    void echo_whenCalledWithMessage_returnsMessages() {
        dao.add(MESSAGE);
        given()
                .port(TEST_PORT)
                .when()
                .get("/api/healthz")
                .then()
                .statusCode(200);
    }

    @Test
    void getMessage_whenCalled_returnsMessagesFromDatabase() {
        dao.add(MESSAGE);
        given()
                .port(TEST_PORT)
                .when()
                .get("/api/message")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body(is(JSON_MESSAGES));
    }

    @Test
    void postMessage_whenCalled_persistsMessage() {
        given()
                .port(TEST_PORT)
                .contentType(ContentType.URLENC)
                .formParam("message", MESSAGE)
                .when()
                .post("/api/message")
                .then()
                .statusCode(204)
                .body(is(""));
        assertThat(dao.getAll(), is(MESSAGES));
    }
}
