package mdhtr.webapplication.server;

import com.google.common.collect.ImmutableSet;
import mdhtr.webapplication.endpoint.HealthEndpoint;
import mdhtr.webapplication.endpoint.MessageEndpoint;
import mdhtr.webapplication.endpoint.TestEndpoint;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import java.util.Set;

@ApplicationPath("")
public class JakartaWsRsApplication extends Application {
    @Override
    public Set<Object> getSingletons() {
        return ImmutableSet.of(new MessageEndpoint(), new HealthEndpoint(), new TestEndpoint());
    }
}
