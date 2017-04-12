package core.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;
import java.util.UUID;

/**
 * Basic implementation of the methods in the DAO interface for Hibernate.
 *
 * @param <T> Object type to be used in DAO
 * @author CatReeena
 */
public abstract class AbstractDAOHibernate<T> implements DAO<T> {
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * {@inheritDoc}
     */
    @Override
    public T find(final UUID id) {
        return entityManager.find(getEntityClass(), id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean delete(final UUID id) {
        boolean result = false;
        T object = entityManager.find(getEntityClass(), id);
        if (object != null) {
            result = true;
            entityManager.remove(object);
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<T> findAll() {
        List<T> objects;
        objects = entityManager.createQuery("from " + getEntityName(),
                getEntityClass()).getResultList();
        return objects;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean add(final T object) {
        entityManager.persist(object);
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean update(final T object) {
        entityManager.merge(object);
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
}
