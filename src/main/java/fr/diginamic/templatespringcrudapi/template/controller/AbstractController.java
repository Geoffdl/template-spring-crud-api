package fr.diginamic.templatespringcrudapi.template.controller;

import fr.diginamic.templatespringcrudapi.template.service.IService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Abstract Controller for CRUD operations
 * @param <T> JPA Entity
 * @param <ID> JPA Entity's id type
 * @param <DTO> DTO mapping of the JPA Entity
 */
public abstract class AbstractController<T, ID, DTO>
{
    /**
     * Returns an instance of an IService implementation containing methods for crud operations and data validation logic
     * @return an instance IService implementation
     */
    protected abstract IService<T, ID, DTO> getService();
    
    /**
     * Finds all entities and returns a complete list of DTO
     * @return list of JPA entity mapped DTO
     */
    @GetMapping
    public ResponseEntity<List<DTO>> findAll() {
        return ResponseEntity.ok(getService().findAll());
    }
    
    /**
     * Finds one
     * @param id entity id
     * @return found entity mapped to dto
     */
    @GetMapping("/{id}")
    public ResponseEntity<DTO> findOne(@PathVariable ID id) {
        return ResponseEntity.ok(getService().findById(id));
    }
    
    /**
     * Insert one
     * @param entity json formatted entity
     * @return entity mapped to dto
     */
    @PostMapping
    public ResponseEntity<DTO> insert(@RequestBody T entity) {
        return ResponseEntity.ok(getService().insert(entity));
    }
    
    /**
     * update one
     * @param entity json formatted entity
     * @return entity mapped to dto
     */
    @PutMapping
    public ResponseEntity<DTO> update(@RequestBody T entity) {
        return ResponseEntity.ok(getService().update(entity));
    }
    
    /**
     *  Delete one
     * @param id entity's id
     * @return 200 on success
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable ID id) {
        getService().delete(id);
        return ResponseEntity.noContent().build();
    }
}
