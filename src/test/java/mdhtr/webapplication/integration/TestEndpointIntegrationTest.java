package mdhtr.webapplication.integration;

import io.restassured.http.ContentType;
import mdhtr.webapplication.server.JettyServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;

class TestEndpointIntegrationTest {
    private static final int TEST_PORT = 9000;
    private static final String MESSAGE = "test message";
    private static final String JSON_MESSAGE_REQUEST_DTO = "{\"message\":\"" + MESSAGE + "\"}";
    private static final String JSON_MESSAGE_RESPONSE_WITH_NO_DATE = "{\"message\":\"" + MESSAGE + "\",\"date\":null}";
    private static final String JSON_MESSAGE_DTO_WITH_UNKNOWN_PROPERTIES = "{\"message\":\"" + MESSAGE + "\",\"extra-property\": \"extra\"}";
    private static final String JSON_MESSAGE_DTO_WITH_DATE = "{\"message\":\"" + MESSAGE + "\",\"date\":\"2020-04-26 16:43:53\"}";

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
                .body(is(JSON_MESSAGE_RESPONSE_WITH_NO_DATE));
    }

    @Test
    void serializeJson_shouldParseObjectWithUnknownProperties() {
        given()
                .port(TEST_PORT)
                .contentType(ContentType.JSON)
                .body(JSON_MESSAGE_DTO_WITH_UNKNOWN_PROPERTIES)
                .when()
                .post("/api/test/json")
                .then()
                .body(is(JSON_MESSAGE_RESPONSE_WITH_NO_DATE))
                .statusCode(200)
        ;
    }

    @Test
    void serializeJson_shouldParseObjectWithDate() {
        given()
                .port(TEST_PORT)
                .contentType(ContentType.JSON)
                .body(JSON_MESSAGE_DTO_WITH_DATE)
                .when()
                .post("/api/test/json")
                .then()
                .statusCode(200)
                .body(is(JSON_MESSAGE_DTO_WITH_DATE));
    }

    @Test
    void serializeWrongJson_shouldHandleJsonParseException() {
        given()
                .port(TEST_PORT)
                .contentType(ContentType.JSON)
                .body("{\"wrong json\"}")
                .when()
                .post("/api/test/jsonError")
                .then()
                .body(is("Could not parse JSON object"))
                .statusCode(400)
        ;
    }

    @Test
    void serializeWrongJson_shouldHandleJsonUnrecognizedPropertyException() {
        given()
                .port(TEST_PORT)
                .contentType(ContentType.JSON)
                .body("{\"unknownProperty\": \"anyValue\"}")
                .when()
                .post("/api/test/jsonError")
                .then()
                .statusCode(400)
                .body(is("Property not recognized"))
        ;
    }

    @Test
    void serializeWrongJson_shouldHandleJsonMappingException() {
        given()
                .port(TEST_PORT)
                .contentType(ContentType.JSON)
                .body("{\"number\": \"notANumber\"}")
                .when()
                .post("/api/test/jsonError")
                .then()
                .statusCode(400)
                .body(is("Could not map JSON object: invalid format"))
        ;
    }

    @Test
    void handleGenericException() {
        given()
                .port(TEST_PORT)
                .when()
                .get("/api/test/throw")
                .then()
                .body(is(""))
                .statusCode(500)
        ;
    }

    @Test
    void handleGenericWebApplicationException() {
        given()
                .port(TEST_PORT)
                .when()
                .get("/api/test/non-existing-endpoint")
                .then()
                .body(is(""))
                .statusCode(404)
        ;
    }

    @Test
    void serializeInvalidJson_shouldThrowDescriptiveBadRequest() {
        given()
                .port(TEST_PORT)
                .contentType(ContentType.JSON)
                .body("{\"number\": \"101\"}")
                .when()
                .post("/api/test/validated")
                .then()
                .header("Content-Type", "application/problem+json")
                .body(containsString("{\"type\":\"/docs/validation_error\",\"title\":\"Validation error\",\"status\":400,\"detail\":"))
                .body(containsString("text: must not be null"))
                .body(containsString("number: Number should be less than or equal to 99"))
                .statusCode(400)
        ;
    }
}
