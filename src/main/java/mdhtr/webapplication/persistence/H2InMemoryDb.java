package mdhtr.webapplication.persistence;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class H2InMemoryDb {
    private static final String DRIVER = "org.h2.Driver";

    private static H2InMemoryDb instance;

    private final String url;
    private final String user;
    private final String password;

    public static void initInstance(String url, String user, String password) {
        if (instance != null) {
            throw new RuntimeException("Static database instance is already initialized.");
        }
        instance = new H2InMemoryDb(url, user, password);
        instance.loadDatabaseDriver();
        instance.createTables();
    }

    public static void destroyInstance() {
        instance.deleteTables();
        instance = null;
    }

    static H2InMemoryDb getInstance() {
        return instance;
    }

    Connection getConnection() {
        return createConnection();
    }

    private void loadDatabaseDriver() {
        try {
            Class.forName(DRIVER).newInstance();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            throw new RuntimeException(String.format("Class for database driver %s not found", DRIVER), e);
        }
    }

    private Connection createConnection() {
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException(String.format("Could not get connection for %s", url), e);
        }
    }

    private void createTables() {
        try {
            Connection connection = getConnection();
            createMessageTable(connection);
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createMessageTable(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute("CREATE TABLE MESSAGE(ID INT AUTO_INCREMENT PRIMARY KEY, MESSAGE VARCHAR(255) DEFAULT '')");
        statement.close();
    }

    private void deleteTables() {
        try {
            Connection connection = getConnection();
            deleteMessageTable(connection);
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteMessageTable(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute("DROP TABLE MESSAGE");
        statement.close();
    }
}
