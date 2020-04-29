package mdhtr.webapplication.endpoint;

import mdhtr.webapplication.endpoint.dto.TestDto;
import mdhtr.webapplication.endpoint.dto.TestErrorDto;
import mdhtr.webapplication.endpoint.dto.TestValidationDto;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/test")
public class TestEndpoint {
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/json")
    public TestDto serializeJson(TestDto testDto) {
        return testDto;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/jsonError")
    public TestErrorDto serializeWrongJson(TestErrorDto testDto) {
        return testDto;
    }

    @GET
    @Path("/throw")
    public void throwException() {
        throw new RuntimeException("custom exception");
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/validated")
    public TestValidationDto validateJson(TestValidationDto testDto) {
        return testDto;
    }
}
