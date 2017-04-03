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


    protected static EntityManager entityManager;
    protected static EntityManagerFactory entityManagerFactory;


    @SuppressWarnings("unchecked")
    protected AbstractDAOHibernate(final String persistenceUnit) {
        this.persistenceUnit = persistenceUnit;
        this.entityBeanType = ((Class) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0]);
        entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnit);
        entityManager = entityManagerFactory.createEntityManager();
    }

    @Override
    public T find(final UUID id) {
        T object = null;
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        object = entityManager.find(entityBeanType, id);
        entityTransaction.commit();
        return object;
    }

    @Override
    public boolean delete(final UUID id) {
        boolean result = false;
        T object = find(id);
        if (object != null) {
            EntityTransaction entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            entityManager.remove(object);
            entityTransaction.commit();
            result = true;
        }
        return result;
    }

    @Override
    public List<T> findAll() {
        List<T> objects = new ArrayList<>();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        objects = entityManager.createQuery("from " + entityBeanType.getName(), entityBeanType).getResultList();
        entityTransaction.commit();
        return objects;
    }

    @Override
    public boolean add(T object) {

        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        entityManager.persist(object);
        entityManager.getTransaction().commit();
        return true;
    }

    @Override
    public boolean update(T object) {

        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        entityManager.merge(object);
        entityTransaction.commit();
        return true;
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
