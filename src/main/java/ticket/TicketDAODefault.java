package ticket;

import core.AbstractDAOJDBC;
import core.DAO;
import user.User;

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
public final class TicketDAODefault extends AbstractDAOJDBC<Ticket, UUID> {
    private static final String TABLE_NAME = "ticket";
    private final DAO<User, UUID> userDAO;

    /**
     * Constructs a new JDBC DAO for {@link Ticket}
     * with {@link User} DAO, default database's url,
     * user's name and user's password.
     *
     * @param userDAO {@code User} DAO for getting user
     */
    public TicketDAODefault(final DAO<User, UUID> userDAO) {
        super(AbstractDAOJDBC.DATABASE_URL,
                AbstractDAOJDBC.DATABASE_USER,
                AbstractDAOJDBC.DATABASE_USER_PASSWORD);
        this.userDAO = userDAO;
    }

    @Override
    public boolean add(final Ticket ticket) {
        boolean result = false;
        try (Connection connection = getConnection()) {

            String sql = "INSERT INTO " + getTableName()
                    + " (id, user_id, row, seat) VALUES (?, ?, ?, ?)";

            try (PreparedStatement statement
                         = connection.prepareStatement(sql)) {
                statement.setObject(1, ticket.getId());
                statement.setObject(2, ticket.getUser().getId());
                statement.setInt(3, ticket.getRow());
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
                statement.setObject(1,
                        ticket.getUser().getId());
                statement.setInt(2,
                        ticket.getRow());
                statement.setInt(3,
                        ticket.getSeat());
                statement.setObject(4,
                        ticket.getId());
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
        return new Ticket(resultSet.getObject("id", UUID.class),
                userDAO.find(resultSet.getObject("user_id", UUID.class)),
                resultSet.getInt("row"),
                resultSet.getInt("seat"));
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }
}
