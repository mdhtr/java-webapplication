package mdhtr.webapplication;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import org.jboss.resteasy.plugins.server.servlet.HttpServletDispatcher;

public class JettyServer {

    public static void main(String[] args) {
        ServletContextHandler contextHandler = new ServletContextHandler();
        contextHandler.setContextPath("/"); // root of the server

        // add DefaultServlet to serve static files from ResourceBase
        contextHandler.setBaseResource(
                Resource.newResource(JettyServer.class.getResource("/mdhtr/webapplication/public/")));
        contextHandler.addServlet(DefaultServlet.class,"/");

        // add RESTEasy servlet at "/api/*".
        ServletHolder restEasyServlet = new ServletHolder(new HttpServletDispatcher());
        restEasyServlet.setInitParameter("resteasy.servlet.mapping.prefix", "/api");
        restEasyServlet.setInitParameter("javax.ws.rs.Application", HelloWorldApplication.class.getName());
        contextHandler.addServlet(restEasyServlet, "/api/*");

        Server server = new Server(8080);
        server.setHandler(contextHandler);
        server.setStopAtShutdown(true); // shut down more gracefully
        server.setStopTimeout(5000); // shut down more gracefully
        try {
            server.start();
            server.join();
        } catch (Exception e ) {
            throw new RuntimeException("Exception while starting embedded Jetty server", e);
        }
    }
}
