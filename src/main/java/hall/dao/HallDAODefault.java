package hall.dao;

import core.dao.AbstractDAOJDBC;
import hall.Hall;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

/**
 * Created by Incy on 15.03.2017.
 */
public class HallDAODefault extends AbstractDAOJDBC<Hall> implements HallDAO {

    private static final String TABLE_NAME = "hall";

    public HallDAODefault()
    {
        super(DATABASE_URL,DATABASE_USER,DATABASE_USER_PASSWORD);
    }

    @Override
    public boolean add(Hall hall) {
        boolean result = false;
        try(Connection connection = getConnection()) {
            String sql = "INSERT INTO " + TABLE_NAME +
                    "(id, name, cinemaId) VALUES (?,?,?)";
            try(PreparedStatement statement = connection.prepareStatement(sql))
            {
                statement.setString(1,hall.getId().toString());
                statement.setString(2,hall.getName());
                statement.setString(3, hall.getCinemaId().toString());
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
    public boolean update(Hall hall) {
        boolean result = false;
        try (Connection connection = getConnection())
        {
            String sql = "UPDATE " + getTableName() +
                    " SET name = ?, cinemaId = ? WHERE id = ?";
            try(PreparedStatement statement = connection.prepareStatement(sql))
            {
                statement.setString(1,hall.getName());
                statement.setString(2, hall.getCinemaId().toString());
                statement.setString(3, hall.getId().toString());
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
    protected Hall getObjectFromResult(ResultSet resultSet) throws SQLException {
        return new Hall(
            UUID.fromString(resultSet.getString("id")),
            resultSet.getString("name"),
            UUID.fromString(resultSet.getString("cinemaId")));
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }


}
