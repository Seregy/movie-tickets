package session.dao;

import core.dao.AbstractDAOJDBC;
import session.Session;

import java.sql.*;
import java.util.UUID;

/**
 * Created by Incy on 15.03.2017.
 */
public class SessionDAODefault extends AbstractDAOJDBC<Session> implements SessionDAO {

    private static final String TABLE_NAME = "session";


    public SessionDAODefault()
    {
        super(DATABASE_URL,DATABASE_USER,DATABASE_USER_PASSWORD);
    }

    @Override
    public boolean add(Session session) {
        boolean result = false;
        try(Connection connection = getConnection()) {
            String sql = "INSERT INTO " + TABLE_NAME +
                    "(id, sessionStart, movieId, hallId) VALUES (?,?,?,?)";
            try(PreparedStatement statement = connection.prepareStatement(sql))
            {
                statement.setString(1,session.getId().toString());
                statement.setTimestamp(2,Timestamp.valueOf(session.getSessionStart()));
                statement.setString(3,session.getMovieId().toString());
                statement.setString(4,session.getHallId().toString());
                statement.executeUpdate();
                result = true;
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean update(Session session) {
        boolean result = false;
        try (Connection connection = getConnection())
        {
            String sql = "UPDATE " + getTableName() +
                    " SET sessionStart = ?, movieId = ?, hallId = ? WHERE id = ?";
            try(PreparedStatement statement = connection.prepareStatement(sql))
            {
                statement.setTimestamp(1, Timestamp.valueOf(session.getSessionStart()));
                statement.setString(2,session.getMovieId().toString());
                statement.setString(3,session.getHallId().toString());
                statement.setString(4,session.getId().toString());
                statement.executeUpdate();
                result = true;
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected Session getObjectFromResult(ResultSet resultSet) throws SQLException {
        return new Session(
                UUID.fromString(resultSet.getString("id")),
                resultSet.getTimestamp("sessionStart").toLocalDateTime(),
                UUID.fromString( resultSet.getString("movieId")),
                UUID.fromString(resultSet.getString("hallId")));
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }
}
