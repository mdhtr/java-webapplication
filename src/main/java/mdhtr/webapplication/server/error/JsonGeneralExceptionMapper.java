package mdhtr.webapplication.server.error;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Slf4j
@Provider
public class JsonGeneralExceptionMapper implements ExceptionMapper<JsonProcessingException> {

    @Override
    public Response toResponse(JsonProcessingException e) {
        log.warn("Exception on request", e);

        if (e instanceof JsonGenerationException) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("").build();
        }

        if (e instanceof JsonParseException) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Could not parse JSON object").build();
        }

        if (e instanceof InvalidFormatException) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Could not map JSON object: invalid format").build();
        }

        if (e instanceof MismatchedInputException) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Could not map JSON object: mismatched input").build();
        }

        if (e instanceof JsonMappingException) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Could not map JSON object").build();
        }

        return Response
                .status(Response.Status.BAD_REQUEST)
                .entity("JSON error")
                .build();
    }
}
