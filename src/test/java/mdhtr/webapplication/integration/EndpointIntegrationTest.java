package mdhtr.webapplication.integration;

import mdhtr.webapplication.JettyServer;
import org.eclipse.jetty.server.Server;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;


class EndpointIntegrationTest {
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

    @Test
    void messageApi_whenCalled_returnsMessage() {
        given()
                .when()
                .get("/api/message")
                .then()
                .statusCode(200)
                .body(is("Text from a REST API"));
    }
}
