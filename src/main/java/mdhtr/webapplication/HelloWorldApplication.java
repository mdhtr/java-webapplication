package mdhtr.webapplication;

import com.google.common.collect.ImmutableSet;
import mdhtr.webapplication.endpoint.HelloWorldEndpoint;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.Set;

@ApplicationPath("")
public class HelloWorldApplication extends Application {
    @Override
    public Set<Object> getSingletons() {
        return ImmutableSet.of(new HelloWorldEndpoint());
    }
}
