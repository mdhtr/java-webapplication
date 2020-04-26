package mdhtr.webapplication.server.error;

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Slf4j
@Provider
public class JsonUnrecognizedPropertyExceptionMapper implements ExceptionMapper<UnrecognizedPropertyException> {
    // This is to override the RESTEasy Builtin Exceptions
    // More info: https://docs.jboss.org/resteasy/docs/3.6.2.Final/userguide/html_single/index.html#overring_resteasy_exceptions
    @Override
    public Response toResponse(UnrecognizedPropertyException exception) {
        log.warn("Exception on request", exception);
        return Response.status(Response.Status.BAD_REQUEST).entity("Property not recognized").build();
    }
}
