package movie.dao;

import core.dao.AbstractDAOJDBC;
import movie.Movie;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

/**
 * Created by Incy on 10.03.2017.
 */
public class MovieDAODefault extends AbstractDAOJDBC<Movie> implements MovieDAO {

    private static final String TABLE_NAME = "movie";

    public MovieDAODefault()
    {
        super(DATABASE_URL,DATABASE_USER,DATABASE_USER_PASSWORD);
    }

    @Override
    public boolean add(final Movie movie) {
        boolean result = false;
        try(Connection connection = getConnection()) {
            String sql = "INSERT INTO " + TABLE_NAME +
                    "(id, name, duration, annotation) VALUES (?,?,?,?)";
            try(PreparedStatement statement = connection.prepareStatement(sql))
            {
                statement.setString(1,movie.getId().toString());
                statement.setString(2,movie.getName());
                statement.setInt(3, movie.getDuration());
                statement.setString(4,movie.getAnnotation());
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
    public boolean update(final Movie movie) {
        boolean result = false;
        try (Connection connection = getConnection())
        {
            String sql = "UPDATE " + getTableName() +
                    " SET name = ?, duration = ?, annotation = ? WHERE id = ?";
            try(PreparedStatement statement = connection.prepareStatement(sql))
            {
                statement.setString(1,movie.getName());
                statement.setInt(2, movie.getDuration());
                statement.setString(3,movie.getAnnotation());
                statement.setString(4,movie.getId().toString());
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
    protected Movie getObjectFromResult(final ResultSet resultSet) throws SQLException {
            return new Movie(
                    UUID.fromString(resultSet.getString("id")),
                    resultSet.getString("name"),
                    resultSet.getInt("duration"),
                    resultSet.getString("annotation"));

    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }
}
