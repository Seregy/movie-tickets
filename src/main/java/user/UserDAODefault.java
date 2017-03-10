package user;

import core.AbstractDAOJDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

/**
 * Default data access object for {@link User}, uses JDBC.
 *
 * @author Seregy
 */
public final class UserDAODefault extends AbstractDAOJDBC<User>
        implements UserDAO {
    private static final String TABLE_NAME = "user";

    /**
     * Constructs a new JDBC DAO for {@link User}
     * with default database's url, user's name and user's password.
     */
    public UserDAODefault() {
        super(AbstractDAOJDBC.DATABASE_URL,
                AbstractDAOJDBC.DATABASE_USER,
                AbstractDAOJDBC.DATABASE_USER_PASSWORD);
    }

    @Override
    public boolean add(final User user) {
        boolean result = false;
        try (Connection connection = getConnection()) {

            String sql = "INSERT INTO " + getTableName()
                    + " (id, full_name, user_name, password, salt, email)"
                    + " VALUES (?, ?, ?, ?, ?, ?)";

            try (PreparedStatement statement
                         = connection.prepareStatement(sql)) {
                statement.setString(1, user.getId().toString());
                statement.setString(2, user.getFullName());
                //noinspection CheckStyle
                statement.setString(3, user.getUserName());
                //noinspection CheckStyle
                statement.setString(4, user.getPassword());
                //noinspection CheckStyle
                statement.setString(5, user.getSalt());
                //noinspection CheckStyle
                statement.setString(6, user.getEmail());
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
    public boolean update(final User user) {
        boolean result = false;

        try (Connection connection = getConnection()) {

            String sql = "UPDATE " + getTableName()
                    + " SET full_name = ?, user_name = ?, password = ?,"
                    + " salt = ?, email = ? WHERE id = ?";

            try (PreparedStatement statement
                         = connection.prepareStatement(sql)) {
                statement.setString(1,
                        user.getFullName());
                statement.setString(2,
                        user.getUserName());
                //noinspection CheckStyle
                statement.setString(3,
                        user.getPassword());
                //noinspection CheckStyle
                statement.setString(4,
                        user.getSalt());
                //noinspection CheckStyle
                statement.setString(5,
                        user.getEmail());
                //noinspection CheckStyle
                statement.setString(6,
                        user.getId().toString());
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
    protected User getObjectFromResult(final ResultSet resultSet)
            throws SQLException {
        return new User(
                UUID.fromString(resultSet.getString("id")),
                resultSet.getString("full_name"),
                resultSet.getString("user_name"),
                resultSet.getString("password"),
                resultSet.getString("salt"),
                resultSet.getString("email"));
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }
}
