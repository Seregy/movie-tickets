package core.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.sql.SQLException;
import java.util.UUID;

/**
 * Created by Shera on 03.04.2017.
 */
public abstract class AbstractDAOHibernate<T> implements DAO<T> {

    protected final String  PERSISTENCE_UNIT = "movieDB";

    private final String persistenceUnit;
    private EntityManager entityManager;

    protected AbstractDAOHibernate(final String persistenceUnit)
    {
        this.persistenceUnit = persistenceUnit;
    }

    @Override
    public T find(final UUID id) {
        T object = null;

        try {
             entityManager = getConnection();

        } catch (Exception e1) {
            e1.printStackTrace();
        }
        entityManager.getTransaction().begin();



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
        } catch (Exception e) {
            e.printStackTrace();
        }

        return object;
    }

    protected EntityManager getConnection() throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("cinemaDB");
        EntityManager entityManager = emf.createEntityManager();
        return entityManager;
    }
}
