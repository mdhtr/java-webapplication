package mdhtr.webapplication.integration;

import io.restassured.http.ContentType;
import mdhtr.webapplication.JettyServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

class TestEndpointIntegrationTest {
    private static final int TEST_PORT = 9000;
    private static final String MESSAGE = "test message";
    private static final String JSON_MESSAGE_REQUEST_DTO = "{\"message\":\"" + MESSAGE + "\"}";

    private JettyServer server;

    @BeforeEach
    void setUp() {
        server = new JettyServer(TEST_PORT);
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

    @Test
    void serializeJson_shouldParseAndReturnObject() {
        given()
                .port(TEST_PORT)
                .contentType(ContentType.JSON)
                .body(JSON_MESSAGE_REQUEST_DTO)
                .when()
                .post("/api/test/json")
                .then()
                .statusCode(200)
                .body(is(JSON_MESSAGE_REQUEST_DTO));
    }
}
