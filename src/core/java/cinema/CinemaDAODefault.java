package core.java.cinema;

import com.mysql.jdbc.Driver;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Seregy on 03.03.2017.
 */
public class CinemaDAODefault implements CinemaDAO {
	private static final String URL = "jdbc:mysql://localhost:3306/db";
	private static final String USER = "root";
	private static final String PASSWORD = "pass";
	private static final String TABLE = "cinema";

	static {
		try {
			DriverManager.registerDriver(new Driver());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Cinema find(int id) {
		Cinema cinema = null;

		try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {

			String sql = "SELECT * FROM " + TABLE + " WHERE id = ?";

			try(PreparedStatement statement = connection.prepareStatement(sql)) {
				statement.setInt(1, id);
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
	public List<Cinema> find(String name) {
		List<Cinema> cinemas = new ArrayList<>();

		try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {

			String sql = "SELECT * FROM " + TABLE + " WHERE name = ?";

			try(PreparedStatement statement = connection.prepareStatement(sql)) {
				statement.setString(1, name);
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

		try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {

			String sql = "SELECT * FROM " + TABLE;

			try(PreparedStatement statement = connection.prepareStatement(sql)) {
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
	public boolean add(Cinema cinema) {
		boolean result = false;
		try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {

			String sql = "INSERT INTO " + TABLE + " (id, name, location) VALUES (?, ?, ?)";

			try(PreparedStatement statement = connection.prepareStatement(sql)) {
				statement.setInt(1, cinema.getId());
				statement.setString(2, cinema.getName());
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
	public boolean update(Cinema cinema) {
		boolean result = false;
		try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {

			String sql = "UPDATE " + TABLE + " SET name = ?, location = ?) WHERE id = ?";

			try(PreparedStatement statement = connection.prepareStatement(sql)) {
				statement.setString(1, cinema.getName());
				statement.setString(2, cinema.getLocation());
				statement.setInt(3, cinema.getId());
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
	public boolean delete(Cinema cinema) {
		boolean result = false;
		try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {

			String sql = "DELETE FROM " + TABLE + " WHERE id = ?";

			try(PreparedStatement statement = connection.prepareStatement(sql)) {
				statement.setInt(1, cinema.getId());
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
