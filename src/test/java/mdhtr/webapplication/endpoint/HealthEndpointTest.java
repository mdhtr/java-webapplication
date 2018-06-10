package mdhtr.webapplication.endpoint;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class HealthEndpointTest {
    private HealthEndpoint endpoint;

    @BeforeEach
    void setUp() {
        endpoint = new HealthEndpoint();
    }

    @Test
    void checkHealth() {
        assertThat(endpoint.checkHealth(), is(HealthEndpoint.HEALTH_OK_MESSAGE));
    }
}
