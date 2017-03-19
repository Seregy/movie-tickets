package core;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *  * A basic implementation of most of the methods in DAO interface for JDBC.
 *
 * @param <T> Object type used in dao
 * @author Seregy
 * @see DAO
 */
public abstract class AbstractDAOJDBC<T>
        implements DAO<T> {
    /**
     * Default url for the database.
     */
    protected static final String DATABASE_URL =
            "jdbc:mysql://localhost:3306/test"
                    + "?useLegacyDatetimeCode=false&serverTimezone=UTC";
    /**
     * Default user for the database.
     */
    protected static final String DATABASE_USER = "user";
    /**
     * Default user's password.
     */
    protected static final String DATABASE_USER_PASSWORD = "pass";

    private final String url;
    private final String userName;
    private final String userPassword;

    /**
     * Constructs a new DAO object for JDBC with specified database's url,
     * user's name and user's password.
     *
     * @param url url for connecting to the database
     * @param userName user's name
     * @param userPassword user's password
     */
    protected AbstractDAOJDBC(final String url,
                              final String userName,
                              final String userPassword) {
        this.url = url;
        this.userName = userName;
        this.userPassword = userPassword;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T find(final UUID id) {
        T object = null;

        try (Connection connection = getConnection()) {

            String sql = "SELECT * FROM " + getTableName() + " WHERE id = ?";

            try (PreparedStatement statement =
                         connection.prepareStatement(sql)) {
                statement.setString(1, id.toString());
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    object = getObjectFromResult(resultSet);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return object;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public List<T> findAll() {
        List<T> objects = new ArrayList<>();

        try (Connection connection = getConnection()) {

            String sql = "SELECT * FROM " + getTableName();

            try (PreparedStatement statement
                         = connection.prepareStatement(sql)) {
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    objects.add(getObjectFromResult(resultSet));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return objects;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public abstract boolean add(T object);

    /**
     * {@inheritDoc}
     */
    @Override
    public abstract boolean update(T object);

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean delete(final UUID id) {
        boolean result = false;
        try (Connection connection = getConnection()) {

            String sql = "DELETE FROM " + getTableName() + " WHERE id = ?";

            try (PreparedStatement statement =
                         connection.prepareStatement(sql)) {
                statement.setString(1, id.toString());
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

    /**
     * Gets {@link Connection} object for the database.
     *
     * @return connection to the database
     * @exception SQLException if a database access error occurs or the url is
     * {@code null}
     */
    protected Connection getConnection() throws SQLException {
        return DriverManager.getConnection(getUrl(),
                getUserName(), getUserPassword());
    }

    /**
     * Creates new object from given {@link ResultSet} and returns it.
     *
     * @param resultSet result of selecting rows with specific id
     * @return new {@link T} object
     * @throws SQLException if given result set
     * doesn't contain necessary columns; if a database access
     * error occurs or if result set is closed
     */
    protected abstract T getObjectFromResult(ResultSet resultSet)
            throws SQLException;

    /**
     * Gets the url used for connecting to the database.
     *
     * @return database's url
     */
    public String getUrl() {
        return url;
    }

    /**
     * Gets user's name.
     *
     * @return name of the user
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Gets user's password.
     *
     * @return user's password
     */
    public String getUserPassword() {
        return userPassword;
    }

    /**
     * Gets the name of the table, used in this DAO.
     *
     * @return name of the table
     */
    protected abstract String getTableName();
}
