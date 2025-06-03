package fr.diginamic.templatespringcrudapi.template.service;

import fr.diginamic.templatespringcrudapi.template.entity.IIdentifiable;

import java.util.List;

/**
 * Service interface for crud operations
 * @param <T> JPA Entity
 * @param <ID> JPA Entity's type
 * @param <DTO> JPA entity DTO mapping
 */
public interface ICrudService<T extends IIdentifiable<ID>, ID, DTO>
{
    /**
     * find all
     * @return a list of entity mapped to DTO
     */
    List<DTO> findAll();
    
    /**
     * insert one
     * @param entity jpa entity
     * @return entity mapped to dto
     */
    DTO insert(T entity);
    /**
     * find by id
     * @param id jpa entity's id
     * @return entity mapped to dto
     */
    DTO findById(ID id);
    /**
     * update one
     * @param entity jpa entity
     * @return entity mapped to dto
     */
    DTO update(T entity);
    
    /**
     * delete one
     * @param id jpa entity's id
     */
    void delete(ID id);
}
