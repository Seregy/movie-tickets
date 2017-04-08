package core.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import java.util.List;
import java.util.UUID;

/**
 * Basic implementation of the methods in the DAO interface for Hibernate.
 *
 * @param <T> Object type to be used in DAO
 * @author CatReeena
 */
public abstract class AbstractDAOHibernate<T> implements DAO<T> {
    private EntityManagerFactory entityManagerFactory;

    /**
     * {@inheritDoc}
     */
    @Override
    public T find(final UUID id) {
        EntityManager entityManager =
                entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        T object = entityManager.find(getEntityClass(), id);
        entityTransaction.commit();
        entityManager.close();
        return object;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean delete(final UUID id) {
        boolean result = false;
        EntityManager entityManager =
                entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        T object = entityManager.find(getEntityClass(), id);
        if (object != null) {
            result = true;
            entityManager.remove(object);
        }
        entityTransaction.commit();
        entityManager.close();
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<T> findAll() {
        List<T> objects;
        EntityManager entityManager =
                entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        objects = entityManager.createQuery("from " + getEntityName(),
                getEntityClass()).getResultList();
        entityTransaction.commit();
        entityManager.close();
        return objects;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean add(final T object) {
        EntityManager entityManager =
                entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        entityManager.persist(object);
        entityManager.getTransaction().commit();
        entityManager.close();
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean update(final T object) {
        EntityManager entityManager =
                entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        entityManager.merge(object);
        entityTransaction.commit();
        entityManager.close();
        return true;
    }

    /**
     * Gets the class of entity used in this DAO.
     *
     * @return entity's class
     */
    public abstract Class<T> getEntityClass();

    /**
     * Gets physical name(table name) of the entity used in this DAO.
     *
     * @return entity's name
     */
    public abstract String getEntityName();

    /**
     * Sets {@link EntityManagerFactory} object to be used by DAO.
     * Used by Spring for IoC
     *
     * @param entityManagerFactory factory
     */
    @Required
    @Autowired
    public void setEntityManagerFactory(
            final EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }
}
