package cinema;

import core.AbstractDAOJDBC;

import java.sql.*;
import java.util.UUID;

/**
 * Default data access object for {@link Cinema}, uses JDBC.
 *
 * @author Seregy
 */
public final class CinemaDAODefault extends AbstractDAOJDBC<Cinema>
        implements CinemaDAO {
    private static final String TABLE_NAME = "cinema";

    /**
     * Constructs a new JDBC DAO for {@link Cinema}
     * with default database's url, user's name and user's password.
     */
    public CinemaDAODefault() {
        super(AbstractDAOJDBC.DATABASE_URL,
                AbstractDAOJDBC.DATABASE_USER,
                AbstractDAOJDBC.DATABASE_USER_PASSWORD);
    }

    @Override
    public boolean add(final Cinema cinema) {
        boolean result = false;
        try (Connection connection = getConnection()) {

            String sql = "INSERT INTO " + getTableName()
                    + " (id, name, location) VALUES (?, ?, ?)";

            try (PreparedStatement statement
                         = connection.prepareStatement(sql)) {
                statement.setString(1, cinema.getId().toString());
                statement.setString(2, cinema.getName());
                //noinspection CheckStyle
                statement.setString(3, cinema.getLocation());
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
    public boolean update(final Cinema cinema) {
        boolean result = false;

        try (Connection connection = getConnection()) {

            String sql = "UPDATE " + getTableName()
                    + " SET name = ?, location = ? WHERE id = ?";

            try (PreparedStatement statement
                         = connection.prepareStatement(sql)) {
                statement.setString(1,
                        cinema.getName());
                statement.setString(2,
                        cinema.getLocation());
                //noinspection CheckStyle
                statement.setString(3,
                        cinema.getId().toString());
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
    protected Cinema getObjectFromResult(final ResultSet resultSet)
            throws SQLException {
        return new Cinema(
                UUID.fromString(resultSet.getString("id")),
                resultSet.getString("name"),
                resultSet.getString("location"));
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }
}
