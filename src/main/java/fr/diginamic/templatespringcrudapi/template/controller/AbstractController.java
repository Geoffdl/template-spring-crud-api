package fr.diginamic.templatespringcrudapi.template.controller;

import fr.diginamic.templatespringcrudapi.template.entity.IIdentifiable;
import fr.diginamic.templatespringcrudapi.template.exception.FunctionnalException;
import fr.diginamic.templatespringcrudapi.template.service.ICrudService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Abstract Controller for CRUD operations
 * @param <T>   JPA Entity
 * @param <ID>  JPA Entity's id type
 * @param <DTO> DTO mapping of the JPA Entity
 */
public abstract class AbstractController<T extends IIdentifiable<ID>, ID, DTO> implements
      IAbstractController<T, ID, DTO>
{
    
    protected final ICrudService<T, ID, DTO> service;
    
    public AbstractController(ICrudService<T, ID, DTO> service)
    {
        this.service = service;
    }
    
    /**
     * Returns an instance of an IService implementation containing methods for crud operations and data validation logic
     * @return an instance IService implementation
     */
    @Override
    public ICrudService<T, ID, DTO> getService()
    {
        return service;
    }
    
    @GetMapping
    @Override
    public ResponseEntity<List<DTO>> findAll()
    {
        return ResponseEntity.ok(getService().findAll());
    }
    
    @GetMapping("/{id}")
    @Override
    public ResponseEntity<DTO> findById(@PathVariable ID id) throws FunctionnalException
    {
        return ResponseEntity.ok(getService().findById(id));
    }
    
    @PostMapping
    @Override
    public ResponseEntity<DTO> insert(@RequestBody T entity) throws FunctionnalException
    {
        return ResponseEntity.ok(getService().insert(entity));
    }
    
    @PutMapping
    @Override
    public ResponseEntity<DTO> update(@RequestBody T entity) throws FunctionnalException
    {
        return ResponseEntity.ok(getService().update(entity));
    }
    
    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<Void> delete(@PathVariable ID id) throws FunctionnalException
    {
        getService().delete(id);
        return ResponseEntity.noContent().build();
    }
}
