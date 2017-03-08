package cinema;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Default data access object for {@link Cinema}, uses MySQL database.
 *
 * @author Seregy
 */
public final class CinemaDAODefault implements CinemaDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/test";
    private static final String USER = "user";
    private static final String PASSWORD = "pass";
    private static final String TABLE = "cinema";

    private static final int ID_COLUMN_INDEX = 1;
    private static final int NAME_COLUMN_INDEX = 2;
    private static final int LOCATION_COLUMN_INDEX = 3;

    @Override
    public Cinema find(final int id) {
        Cinema cinema = null;

        try (Connection connection =
                     DriverManager.getConnection(URL, USER, PASSWORD)) {

            String sql = "SELECT * FROM " + TABLE + " WHERE id = ?";

            try (PreparedStatement statement =
                         connection.prepareStatement(sql)) {
                statement.setInt(ID_COLUMN_INDEX, id);
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    cinema = new Cinema(resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getString("location"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cinema;
    }

    @Override
    public List<Cinema> find(final String name) {
        List<Cinema> cinemas = new ArrayList<>();

        try (Connection connection =
                     DriverManager.getConnection(URL, USER, PASSWORD)) {

            String sql = "SELECT * FROM " + TABLE + " WHERE name = ?";

            try (PreparedStatement statement
                         = connection.prepareStatement(sql)) {
                statement.setString(ID_COLUMN_INDEX, name);
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    cinemas.add(new Cinema(resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getString("location")));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cinemas;
    }

    @Override
    public List<Cinema> findAll() {
        List<Cinema> cinemas = new ArrayList<>();

        try (Connection connection
                     = DriverManager.getConnection(URL, USER, PASSWORD)) {

            String sql = "SELECT * FROM " + TABLE;

            try (PreparedStatement statement
                         = connection.prepareStatement(sql)) {
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    cinemas.add(new Cinema(resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getString("location")));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cinemas;
    }

    @Override
    public boolean add(final Cinema cinema) {
        boolean result = false;
        try (Connection connection =
                     DriverManager.getConnection(URL, USER, PASSWORD)) {

            String sql = "INSERT INTO " + TABLE
                    + " (id, name, location) VALUES (?, ?, ?)";

            try (PreparedStatement statement
                         = connection.prepareStatement(sql)) {
                statement.setInt(ID_COLUMN_INDEX, cinema.getId());
                statement.setString(NAME_COLUMN_INDEX, cinema.getName());
                statement.setString(LOCATION_COLUMN_INDEX,
                        cinema.getLocation());
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
        try (Connection connection =
                     DriverManager.getConnection(URL, USER, PASSWORD)) {

            String sql = "UPDATE " + TABLE
                    + " SET name = ?, location = ? WHERE id = ?";

            try (PreparedStatement statement
                         = connection.prepareStatement(sql)) {
                statement.setString(ID_COLUMN_INDEX, cinema.getName());
                statement.setString(NAME_COLUMN_INDEX, cinema.getLocation());
                statement.setInt(LOCATION_COLUMN_INDEX, cinema.getId());
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
    public boolean delete(final Cinema cinema) {
        boolean result = false;
        try (Connection connection =
                     DriverManager.getConnection(URL, USER, PASSWORD)) {

            String sql = "DELETE FROM " + TABLE + " WHERE id = ?";

            try (PreparedStatement statement =
                         connection.prepareStatement(sql)) {
                statement.setInt(ID_COLUMN_INDEX, cinema.getId());
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
}
