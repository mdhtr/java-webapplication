package mdhtr.webapplication.endpoint;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/")
public class HelloWorldEndpoint {
    @GET
    @Path("/message")
    public String getMessage() {
        return "Text from a REST API";
    }
}
