package mdhtr.webapplication.endpoint;

import com.google.common.annotations.VisibleForTesting;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/healthz")
public class HealthEndpoint {
    @VisibleForTesting
    static final String HEALTH_OK_MESSAGE = "Health of the server is good.";

    @GET
    @Path("/")
    public String checkHealth() {
        return HEALTH_OK_MESSAGE;
    }
}
