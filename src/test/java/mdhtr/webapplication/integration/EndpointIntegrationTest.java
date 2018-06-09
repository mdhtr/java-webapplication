package mdhtr.webapplication.integration;

import com.google.common.collect.ImmutableList;
import io.restassured.http.ContentType;
import jdk.nashorn.internal.ir.annotations.Ignore;
import mdhtr.webapplication.JettyServer;
import mdhtr.webapplication.persistence.Message;
import mdhtr.webapplication.persistence.MessageDao;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


class EndpointIntegrationTest {
    private static String MESSAGE = "test message";
    private static List<Message> MESSAGES = ImmutableList.of(new Message(1, MESSAGE));
    private static String JSON_MESSAGES = "[{\"id\": \"1\", \"message\": \"Hello\"}]";
    private MessageDao dao = new MessageDao();
    private JettyServer server;

    @BeforeEach
    void setUp() {
        server = new JettyServer(9000);
        try {
            server.start();
        } catch (Exception e) {
            throw new RuntimeException("Exception while starting test server", e);
        }
    }

    @AfterEach
    void tearDown() {
        try {
            server.stop();
        } catch (Exception e) {
            throw new RuntimeException("Exception while stopping test server", e);
        }
    }

    @Disabled("not fixed yet")
    @Test
    void getMessage_whenCalled_returnsMessagesFromDatabase() {
        dao.add(MESSAGE);
        given()
                .when()
                .get("/api/message")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body(is(JSON_MESSAGES));
    }

    @Disabled("not fixed yet")
    @Test
    void postMessage_whenCalled_persistsMessage() {
        given()
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
