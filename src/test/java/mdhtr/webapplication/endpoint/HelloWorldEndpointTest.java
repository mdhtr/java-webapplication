package mdhtr.webapplication.endpoint;

import com.google.common.collect.ImmutableList;
import mdhtr.webapplication.persistence.Message;
import mdhtr.webapplication.persistence.MessageDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HelloWorldEndpointTest {
    private static String MESSAGE = "test message";
    private static List<Message> MESSAGES = ImmutableList.of(new Message(1, MESSAGE));
    @Mock private MessageDao dao;
    private HelloWorldEndpoint endpoint;

    @BeforeEach
    void setUp() {
        endpoint = new HelloWorldEndpoint(dao);
    }

    @Test
    void getMessages_whenCalled_returnsMessages() {
        when(dao.getAll()).thenReturn(MESSAGES);
        assertThat(endpoint.getMessages(), is(MESSAGES));
        Mockito.verify(dao, times(1)).getAll();
        verifyNoMoreInteractions(dao);
    }

    @Test
    void addMessage_whenCalled_passesOnMessageToDao() {
        endpoint.addMessage(MESSAGE);
        Mockito.verify(dao, times(1)).add(Mockito.eq(MESSAGE));
        Mockito.verifyNoMoreInteractions(dao);
    }
}
