package org.basham.dao;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;

import org.apache.log4j.Logger;
import org.basham.domain.DomainEntity;

import com.google.common.base.Optional;

/**
 * The AbstractJpaDAO class provides a base class for all JPA-based Data Access Objects.
 * 
 * @param <K>  The type of database identifier for this Entity.
 * @param <E>  The type of Entity this DAO handles.
 * 
 * @author <a href='mailto:basham47@gmail.com'>Bryan Basham</a>
 */
public abstract class AbstractJpaDAO<K extends Serializable, E extends Serializable> {
	private static final Logger LOG = Logger.getLogger(AbstractJpaDAO.class);

	//
    // DI Attributes
    //

	@PersistenceContext
	private EntityManager entityManager;

	//
    // Attributes
    //

	private final Class<E> entityClass;
	private final String retrieveAllQuery;

	//
    // Constructors
    //

	/**
	 * Construct a JPA DAO that specifies the type of Entity that the 
	 * DAO handles.  This forces each subclass to specify the Entity type.
	 * 
	 * @param entityClassIn  The Java class of the Entity.
	 */
	protected AbstractJpaDAO(final Class<E> entityClassIn) {
		entityClass = checkNotNull(entityClassIn);
		retrieveAllQuery = String.format("SELECT e FROM %s e", entityClass.getName());
	}

	//
    // AbstractJpaDAO methods
    //

	/**
	 * Retrieve all Entities of this type.
	 * 
	 * @return  A complete list of Entities in the database.
	 */
	@SuppressWarnings("unchecked")
	public List<E> findAll() {
    	// perform query
		return entityManager.createQuery(retrieveAllQuery).getResultList();
	}

	/**
	 * Retrieves an Entity from the database id.
	 * 
	 * @param id  The database id
	 * 
	 * @return  The Entity for this id
	 */
	public E findById(final K id) {
		return entityManager.find(entityClass, id);
	}

	public Optional<E> findWithDepth(long id, String... fetchRelations) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<E> criteriaQuery = criteriaBuilder.createQuery(entityClass);
        Root<E> root = criteriaQuery.from(entityClass);
        // add relationships
        for (String relation : fetchRelations) {
            FetchParent<E, E> fetch = root;
            for (String pathSegment : relation.split(".")) {
                fetch = fetch.fetch(pathSegment, JoinType.LEFT);
            }
        }
        // add WHERE clause
        criteriaQuery.where(criteriaBuilder.equal(root.get("id"), id));
        //
        return getOptionalResult(entityManager.createQuery(criteriaQuery));
    }

	/**
	 * Create a new Entity in the database.
	 * 
	 * @param entity  The Entity to create in the database.
	 */
	public void create(final E entity) {
		entityManager.persist(entity);
	}

	/**
	 * Update an existing Entity in the database.
	 * 
	 * @param entity  The Entity to update in the database.
	 */
	public void update(final E entity) {
		entityManager.merge(entity);
	}

	/**
	 * Delete an existing Entity in the database.
	 * 
	 * @param entity  The Entity to delete in the database.
	 */
	public void delete(final E entity) {
		entityManager.remove(entity);
	}

	/**
	 * Delete an existing Entity, specified by the id, in the database.
	 * 
	 * @param entityId  The id of the Entity to delete in the database.
	 */
	public void deleteById(final K entityId) {
		final E entity = findById(entityId);
		delete(entity);
	}

	//
	// General CRUD operations on any JPA Entity
	//

	/**
	 * Saves a single type of {@link DomainEntity} to the database.
	 * 
	 * @param e  the entity to save
	 */
	public void saveEntity(final DomainEntity<K> e) {
		if (e.isNew()) {
			entityManager.persist(e);
			if (LOG.isTraceEnabled()) {
				LOG.trace("Created: " + e.toString());
			}
		} else {
			entityManager.merge(e);
			if (LOG.isTraceEnabled()) {
				LOG.trace("Updated: " + e.toString());
			}
		}
	}

	/**
	 * Deletes a single {@link DomainEntity} from the database.
	 * 
	 * @param e  the entity to delete
	 */
	public void deleteEntity(final DomainEntity<K> e) {
		entityManager.remove(e);
	}

	//
	// Helper methods
	//

	/**
	 * Gets the JPA Entity Manager for this DAO.
	 */
	protected EntityManager getEntityManager() {
		return entityManager;
	}

	/**
	 * Gets a named, Entity-specific query.
	 * 
	 * @param <ET>  The type of Entity for this query.
	 * 
	 * @param entityType  The class of the Entity for this query.
	 * @param queryName  The name of an Entity query.  Must be declared on the Entity class.
	 * 
	 * @return a {@link TypedQuery} for this Entity class
	 */
	protected <ET> TypedQuery<ET> getEntityQuery(final Class<ET> entityType, final String queryName) {
		return entityManager.createNamedQuery(queryName, entityType);
	}

	/**
	 * Gets a named, Entity-specific query.
	 * 
	 * @param queryName  The name of an Entity query.  Must be declared on the Entity class.
	 * 
	 * @return a {@link TypedQuery} for this Entity class
	 */
	protected TypedQuery<E> getEntityQuery(final String queryName) {
		return getEntityQuery(entityClass, queryName);
	}

	/**
	 * Gets a named, Entity-specific query used for retrieving counts, which are always returned
	 * by JPA as a long.
	 * 
	 * @param queryName  The name of an Entity query.  Must be declared on the Entity class.
	 * 
	 * @return a {@link TypedQuery} of type {@link Long}
	 */
	protected TypedQuery<Long> getCountQuery(final String queryName) {
		return entityManager.createNamedQuery(queryName, Long.class);
	}

	/**
	 * Get an optional result from a query.
	 * 
	 * @param <ET>  The type of Entity for this query.
	 * 
	 * @param entityType  The class of the Entity for this query.
	 * @param query  The typed query object.
	 * 
	 * @return a single Entity or {@code null} if none was found
	 */
	protected <ET> Optional<ET> getOptionalResult(final Class<ET> entityType, final TypedQuery<ET> query) {        
        query.setMaxResults(1);
        List<ET> result = query.getResultList();
        if (result.isEmpty()) {
            return Optional.absent();
        }
        return Optional.of(result.get(0));
    }

	/**
	 * Get  an optional result from a query.
	 * 
	 * @param query  The typed query object.
	 * 
	 * @return a single Entity or {@code null} if none was found
	 */
	protected Optional<E> getOptionalResult(TypedQuery<E> query) {        
        return getOptionalResult(entityClass, query);
    }
	
}
