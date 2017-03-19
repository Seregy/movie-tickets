package ticket.dao;

import core.dao.AbstractDAOJDBC;
import ticket.Ticket;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

/**
 * Default data access object for {@link Ticket}, uses JDBC.
 *
 * @author Seregy
 */
public final class TicketDAODefault extends AbstractDAOJDBC<Ticket>
        implements TicketDAO {
    private static final String TABLE_NAME = "ticket";

    /**
     * Constructs a new JDBC DAO for {@link Ticket}
     * with default database's url,
     * user's name and user's password.
     */
    public TicketDAODefault() {
        super(AbstractDAOJDBC.DATABASE_URL,
                AbstractDAOJDBC.DATABASE_USER,
                AbstractDAOJDBC.DATABASE_USER_PASSWORD);
    }

    @Override
    public boolean add(final Ticket ticket) {
        boolean result = false;
        try (Connection connection = getConnection()) {

            String sql = "INSERT INTO " + getTableName()
                    + " (id, user_id, row, seat) VALUES (?, ?, ?, ?)";

            try (PreparedStatement statement
                         = connection.prepareStatement(sql)) {
                statement.setString(1, ticket.getId().toString());
                statement.setString(2, ticket.getUserId().toString());
                //noinspection CheckStyle
                statement.setInt(3, ticket.getRow());
                //noinspection CheckStyle
                statement.setInt(4, ticket.getSeat());
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
    public boolean update(final Ticket ticket) {
        boolean result = false;

        try (Connection connection = getConnection()) {

            String sql = "UPDATE " + getTableName()
                    + " SET user_id = ?, row = ?, seat = ? WHERE id = ?";

            try (PreparedStatement statement
                         = connection.prepareStatement(sql)) {
                statement.setString(1,
                        ticket.getUserId().toString());
                statement.setInt(2,
                        ticket.getRow());
                //noinspection CheckStyle
                statement.setInt(3,
                        ticket.getSeat());
                //noinspection CheckStyle
                statement.setString(4,
                        ticket.getId().toString());
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
    protected Ticket getObjectFromResult(final ResultSet resultSet)
            throws SQLException {
        return new Ticket(UUID.fromString(resultSet.getString("id")),
                UUID.fromString(resultSet.getString("user_id")),
                resultSet.getInt("row"),
                resultSet.getInt("seat"));
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }
}
