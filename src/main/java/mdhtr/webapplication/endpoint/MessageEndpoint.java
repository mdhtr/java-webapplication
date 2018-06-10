package mdhtr.webapplication.endpoint;

import lombok.RequiredArgsConstructor;
import mdhtr.webapplication.persistence.Message;
import mdhtr.webapplication.persistence.MessageDao;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
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
        dao.add(message);
    }
}
