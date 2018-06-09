package mdhtr.webapplication.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MessageDao {

    public void add(String message) {
        try(Connection connection = H2InMemoryDb.getInstance().getConnection()){
            String insertQuery = "INSERT INTO MESSAGE" + "(MESSAGE) values" + "(?)";
            try(PreparedStatement insertPreparedStatement = connection.prepareStatement(insertQuery)) {
                insertPreparedStatement.setString(1, message);
                insertPreparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(
                        String.format("Exception while trying to insert into message %s into database", message), e);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Exception while trying to connect to database", e);
        }
    }

    public List<Message> getAll() {
        try(Connection connection = H2InMemoryDb.getInstance().getConnection()){
            String selectQuery = "select * from MESSAGE";
            try(PreparedStatement selectPreparedStatement = connection.prepareStatement(selectQuery)) {
                ResultSet rs = selectPreparedStatement.executeQuery();
                List<Message> messages = new ArrayList<>();
                while (rs.next()) {
                    messages.add(new Message(rs.getInt("id"), rs.getString("message")));
                }
                return messages;
            } catch (SQLException e) {
                throw new RuntimeException("Exception while trying to get all messages from database", e);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Exception while trying to connect to database", e);
        }
    }
}
