package mdhtr.webapplication;

import mdhtr.webapplication.persistence.H2InMemoryDb;
import mdhtr.webapplication.server.JettyServer;

public class Launcher {
    private static final int DEFAULT_PORT = 8080;
    private static final String DB_URL = "jdbc:h2:mem:mdhtr;DB_CLOSE_DELAY=-1";
    private static final String DB_USER = "";
    private static final String DB_PASSWORD = "";

    public static void main(String[] args) {
        H2InMemoryDb.initInstance(DB_URL, DB_USER, DB_PASSWORD);
        JettyServer server = new JettyServer(DEFAULT_PORT);
        try {
            server.start();
        } catch (Exception e) {
            throw new RuntimeException("Exception while starting embedded Jetty server", e);
        }
    }
}
