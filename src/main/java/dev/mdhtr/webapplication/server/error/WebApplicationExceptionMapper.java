package dev.mdhtr.webapplication.server.error;

import lombok.extern.slf4j.Slf4j;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Slf4j
@Provider
public class WebApplicationExceptionMapper implements ExceptionMapper<WebApplicationException> {
    @Override
    public Response toResponse(WebApplicationException e) {
        log.warn("Exception on request", e);
        return Response
                .status(e.getResponse().getStatus())
                .entity("")
                .build();
    }
}
