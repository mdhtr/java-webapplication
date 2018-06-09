package mdhtr.webapplication.endpoint;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/")
public class HelloWorldEndpoint {
    @GET
    @Path("/message")
    public Response getMessage() {
        String result = "Text from a REST API";
        return Response.status(200).entity(result).build();
    }
}
