package dev.mdhtr.webapplication.endpoint;

import lombok.RequiredArgsConstructor;
import dev.mdhtr.webapplication.persistence.Message;
import dev.mdhtr.webapplication.persistence.MessageDao;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@Path("/message")
@RequiredArgsConstructor
public class MessageEndpoint {
    private final MessageDao dao;

    public MessageEndpoint() {
        dao = new MessageDao();
    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Message> getMessages() {
        return dao.getAll();
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Path("/")
    public void addMessage(@FormParam("message") String message) {
        if (!message.isBlank()) {
            dao.add(message);
        }
    }
}
