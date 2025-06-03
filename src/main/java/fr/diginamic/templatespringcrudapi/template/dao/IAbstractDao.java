package fr.diginamic.templatespringcrudapi.template.dao;

import java.util.List;

public interface IAbstractDao<T, ID>
{
    List<T> findAll();
    
    T findById(ID id);
    
    T save(T entity);
    
    T update(ID id, T entity);
    
    void delete(ID id);
}
