package core.dao;

import cinema.Cinema;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.transaction.TransactionManager;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Shera on 03.04.2017.
 */
public abstract class AbstractDAOHibernate<T> implements DAO<T> {

    protected static final String PERSISTENCE_UNIT = "cinemaDB";
    private final String persistenceUnit;
    private final Class<T> entityBeanType;
    ;

    private EntityManager entityManager;
    private EntityManagerFactory entityManagerFactory;


    @SuppressWarnings("unchecked")
    protected AbstractDAOHibernate(final String persistenceUnit) {
        this.persistenceUnit = persistenceUnit;
        this.entityBeanType = ((Class) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0]);
    }

    @Override
    public T find(final UUID id) {
        T object = null;

        try {
            createFactory();
            createManager();
            EntityTransaction entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();

            try {
                object = entityManager.find(entityBeanType, id);
                entityTransaction.commit();
            } catch (NullPointerException e) {
                e.printStackTrace();
                entityTransaction.rollback();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return object;
    }

    @Override
    public boolean delete(final UUID id) {
        boolean result = false;
        try {
            createFactory();
            createManager();
            EntityTransaction entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            T object = find(id);
            if (object != null) {
                try {
                    entityManager.remove(object);
                    entityManager.getTransaction().commit();
                    result = true;
                }catch (Exception e)
                {
                    e.printStackTrace();
                    entityTransaction.rollback();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<T> findAll() {
        List<T> objects = new ArrayList<>();
        try {
            createFactory();
            createManager();
            EntityTransaction entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            try {
                objects = entityManager.createQuery("from " + entityBeanType.getName(), entityBeanType).getResultList();
                entityTransaction.commit();
            } catch (Exception e) {
                e.printStackTrace();
                entityTransaction.rollback();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return objects;
    }

    @Override
    public boolean add(T object) {
        boolean result = false;
        try {
            createFactory();
            createManager();
            EntityTransaction entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            try {
                entityManager.persist(object);
                entityManager.getTransaction().commit();
                result = true;
            }
            catch (Exception e) {
                e.printStackTrace();
                entityTransaction.rollback();
            }
        }
            catch (Exception e) {
                e.printStackTrace();
            }
        return result;
    }

    @Override
    public boolean update(T object) {
       return add(object);
    }

    private void createFactory() throws Exception {

        if (entityManagerFactory == null) {
            entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnit);
        }
    }

    private void createManager() throws Exception
    {
        if(entityManager==null) {
            entityManager = entityManagerFactory.createEntityManager();
        }
    }

    public String getPersistenceUnit() {
        return persistenceUnit;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public Class<T> getEntityBeanType() {
        return entityBeanType;
    }
}
