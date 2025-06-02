package fr.diginamic.templatespringcrudapi.template.service;

import java.util.List;

public interface IService<T, ID, DTO>
{
    List<DTO> findAll();
    
    DTO findOne();
    
    DTO insert(T entity);
    
    DTO findById(ID id);
    
    DTO update(T entity);
    
    void delete(ID id);
}
