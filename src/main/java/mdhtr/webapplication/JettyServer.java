package mdhtr.webapplication;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ResourceHandler;

public class JettyServer {

    public static void main(String[] args) {
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase("/"); // root of the file system
        resourceHandler.setDirectoriesListed(true);

        ContextHandler contextHandler = new ContextHandler();
        contextHandler.setContextPath("/"); // root of the server
        contextHandler.setHandler(resourceHandler);

        Server server = new Server(8080);
        server.setHandler(contextHandler);
        try {
            server.start();
            server.join();
        } catch (Exception e ) {
            throw new RuntimeException("Exception while starting embedded Jetty server", e);
        }
    }
}
