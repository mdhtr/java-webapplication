package dev.mdhtr.webapplication.endpoint;

import dev.mdhtr.webapplication.endpoint.dto.TestDto;
import dev.mdhtr.webapplication.endpoint.dto.TestErrorDto;
import dev.mdhtr.webapplication.endpoint.dto.TestValidationDto;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

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
