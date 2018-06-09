package mdhtr.webapplication;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import org.jboss.resteasy.plugins.server.servlet.HttpServletDispatcher;

public class JettyServer {
    private final Server server;

    public JettyServer(int port) {
        server = createServer(port);
    }

    private Server createServer(int port) {
        ServletContextHandler contextHandler = new ServletContextHandler();
        contextHandler.setContextPath("/"); // root of the server

        // add DefaultServlet to serve static files from ResourceBase
        contextHandler.setBaseResource(
                Resource.newResource(JettyServer.class.getResource("/mdhtr/webapplication/public/")));
        contextHandler.addServlet(DefaultServlet.class, "/");

        // add RESTEasy servlet at "/api/*".
        ServletHolder restEasyServlet = new ServletHolder(new HttpServletDispatcher());
        restEasyServlet.setInitParameter("resteasy.servlet.mapping.prefix", "/api");
        restEasyServlet.setInitParameter("javax.ws.rs.Application", HelloWorldApplication.class.getName());
        contextHandler.addServlet(restEasyServlet, "/api/*");

        Server server = new Server(port);
        server.setHandler(contextHandler);
        server.setStopAtShutdown(true); // shut down more gracefully
        server.setStopTimeout(5000); // shut down more gracefully

        return server;
    }

    public static void main(String[] args) {
        try {
            JettyServer server = new JettyServer(8080);
            server.start();
        } catch (Exception e) {
            throw new RuntimeException("Exception while starting embedded Jetty server", e);
        }
    }

    public void start() throws Exception {
        server.start();
    }

    public void stop() throws Exception {
        server.stop();
    }
}
