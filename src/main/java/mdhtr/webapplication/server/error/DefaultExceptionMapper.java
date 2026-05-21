package mdhtr.webapplication.server.error;

import lombok.extern.slf4j.Slf4j;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Slf4j
@Provider
public class DefaultExceptionMapper implements ExceptionMapper<Exception> {
    @Override
    public Response toResponse(Exception e) {
        log.warn("Exception on request", e);
        return Response
                .status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("")
                .build();
    }
}
