package mdhtr.webapplication.endpoint;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class HelloWorldEndpointTest {
    private HelloWorldEndpoint endpoint;

    @BeforeEach
    void setUp() {
        endpoint = new HelloWorldEndpoint();
    }

    @Test
    void getMessage_whenCalled_returnsMessage() {
        assertThat(endpoint.getMessage(), is("Text from a REST API"));
    }
}
