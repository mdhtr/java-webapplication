package mdhtr.webapplication.server.error;

import lombok.extern.slf4j.Slf4j;
import mdhtr.webapplication.server.error.dto.ProblemDetailsDto;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import java.net.URI;

@Slf4j
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {
    @Override
    public Response toResponse(ConstraintViolationException exception) {
        log.warn("Exception on request", exception);
        return Response
                .status(Response.Status.BAD_REQUEST)
                .type("application/problem+json")
                .entity(ProblemDetailsDto.builder()
                        .type(URI.create("/docs/validation_error"))
                        .status(Response.Status.BAD_REQUEST.getStatusCode())
                        .title("Validation error")
                        .detail(exception.getMessage())
                        .build())
                .build();
    }
}
