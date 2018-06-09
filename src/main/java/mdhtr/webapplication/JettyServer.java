package mdhtr.webapplication;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import org.jboss.resteasy.plugins.server.servlet.HttpServletDispatcher;

import static org.jboss.resteasy.plugins.server.servlet.ResteasyContextParameters.RESTEASY_SERVLET_MAPPING_PREFIX;

@Slf4j
public class JettyServer {
    private static final String JAVAX_WS_RS_APPLICATION = "javax.ws.rs.Application";

    private static final int STOP_TIMEOUT_MILLISECONDS = 5000;
    private static final int DEFAULT_PORT = 8080;
    private static final String STATIC_CONTENT_RESOURCE_PATH = "/mdhtr/webapplication/public/";

    private static final String CONTEXT_PATH = "/";
    private static final String STATIC_CONTENT_BASE_PATH = "/";
    private static final String REST_API_BASE_PATH = "/api";
    private static final String ANY_PATH = "/*";

    private final Server server;
    private int port;

    public static void main(String[] args) {
        try {
            JettyServer server = new JettyServer(DEFAULT_PORT);
            server.start();
        } catch (Exception e) {
            throw new RuntimeException("Exception while starting embedded Jetty server", e);
        }
    }

    public JettyServer(int port) {
        this.port = port;
        this.server = createServer();
    }

    public void start() throws Exception {
        server.start();
        log.info("Jetty Server started on port {}", port);
    }

    public void stop() throws Exception {
        server.stop();
    }

    private Server createServer() {
        Server server = new Server(port);
        enableGracefulShutdown(server);
        server.setHandler(createContextHandler());
        return server;
    }

    private void enableGracefulShutdown(Server server) {
        server.setStopAtShutdown(true);
        server.setStopTimeout(STOP_TIMEOUT_MILLISECONDS);
    }

    private ServletContextHandler createContextHandler() {
        ServletContextHandler contextHandler = new ServletContextHandler();
        contextHandler.setContextPath(CONTEXT_PATH);
        addStaticFileServlet(contextHandler);
        addRestEasyServlet(contextHandler);
        return contextHandler;
    }

    private void addStaticFileServlet(ServletContextHandler contextHandler) {
        contextHandler.setBaseResource(
                Resource.newResource(JettyServer.class.getResource(STATIC_CONTENT_RESOURCE_PATH)));
        contextHandler.addServlet(DefaultServlet.class, STATIC_CONTENT_BASE_PATH);
    }

    private void addRestEasyServlet(ServletContextHandler contextHandler) {
        ServletHolder restEasyServlet = new ServletHolder(new HttpServletDispatcher());
        restEasyServlet.setInitParameter(RESTEASY_SERVLET_MAPPING_PREFIX, REST_API_BASE_PATH);
        restEasyServlet.setInitParameter(JAVAX_WS_RS_APPLICATION, HelloWorldApplication.class.getName());
        contextHandler.addServlet(restEasyServlet, REST_API_BASE_PATH + ANY_PATH);
    }
}
