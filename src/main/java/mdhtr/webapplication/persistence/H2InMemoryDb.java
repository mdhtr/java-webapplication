package mdhtr.webapplication.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

class H2InMemoryDb {
    private static final String DRIVER = "org.h2.Driver";
    private static final String URL = "jdbc:h2:mem:mdhtr;DB_CLOSE_DELAY=-1";
    private static final String USER = "";
    private static final String PASSWORD = "";

    private static H2InMemoryDb instance = new H2InMemoryDb(DRIVER, URL, USER, PASSWORD);

    private final String url;
    private final String user;
    private final String password;

    static H2InMemoryDb getInstance() {
        return instance;
    }

    private H2InMemoryDb(String driver, String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;

        loadDatabaseDriver(driver);
        createTables();
    }

    private void loadDatabaseDriver(String driver) {
        try {
            Class.forName(driver).newInstance();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            throw new RuntimeException(String.format("Class for database driver %s not found", driver), e);
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

    Connection getConnection() {
        return createConnection();
    }
}
