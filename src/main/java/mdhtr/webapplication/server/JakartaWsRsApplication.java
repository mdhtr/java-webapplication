package mdhtr.webapplication.server;

import mdhtr.webapplication.endpoint.HealthEndpoint;
import mdhtr.webapplication.endpoint.MessageEndpoint;
import mdhtr.webapplication.endpoint.TestEndpoint;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import java.util.Set;

@ApplicationPath("")
public class JakartaWsRsApplication extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        return Set.of(MessageEndpoint.class, HealthEndpoint.class, TestEndpoint.class);
    }
}
