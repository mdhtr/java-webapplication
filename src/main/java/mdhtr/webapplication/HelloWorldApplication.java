package mdhtr.webapplication;

import mdhtr.webapplication.endpoint.HelloWorldEndpoint;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("")
public class HelloWorldApplication extends Application {
    @Override
    public Set<Object> getSingletons() {
        HashSet<Object> set = new HashSet<>();
        set.add(new HelloWorldEndpoint());
        return set;
    }
}
