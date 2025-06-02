package fr.diginamic.templatespringcrudapi.template.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;

public abstract class AbstractDao<T, ID> implements IDao<T, ID>
{
    
    private final Class<T> entityClass;
    @PersistenceContext
    protected EntityManager em;
    
    protected AbstractDao(Class<T> entityClass)
    {
        this.entityClass = entityClass;
    }
    
    @Override public List<T> findAll()
    {
        
        String querry = "SELECT e FROM " + entityClass.getSimpleName() + " e";
        return em.createQuery(querry, entityClass).getResultList();
    }
    
    @Override public T findById(ID id)
    {
        return em.find(entityClass, id);
    }
    
    @Override @Transactional public T save(T entity)
    {
        em.persist(entity);
        return entity;
    }
    
    @Override public abstract T update(ID id, T entity);
    
    @Override public void delete(ID id)
    {
        em.remove(findById(id));
    }
}
