package fr.diginamic.templatespringcrudapi.template.controller;

import fr.diginamic.templatespringcrudapi.template.entity.IIdentifiable;
import fr.diginamic.templatespringcrudapi.template.exception.FunctionnalException;
import fr.diginamic.templatespringcrudapi.template.service.ICrudService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/default")
public interface IAbstractController<T extends IIdentifiable<ID>, ID, DTO>
{
    ICrudService<T, ID, DTO> getService();
    
    /**
     * Finds all entities and returns a complete list of DTO
     * @return list of JPA entity mapped DTO
     */
    @GetMapping
    ResponseEntity<List<DTO>> findAll();
    
    /**
     * Finds one
     * @param id entity id
     * @return found entity mapped to dto
     */
    @GetMapping("/{id}")
    ResponseEntity<DTO> findById(@PathVariable ID id) throws FunctionnalException;
    
    /**
     * Insert one
     * @param entity json formatted entity
     * @return entity mapped to dto
     */
    @PostMapping
    ResponseEntity<DTO> insert(@RequestBody T entity) throws FunctionnalException;
    
    /**
     * update one
     * @param entity json formatted entity
     * @return entity mapped to dto
     */
    @PutMapping
    ResponseEntity<DTO> update(@RequestBody T entity) throws FunctionnalException;
    
    /**
     * Delete one
     * @param id entity's id
     * @return 200 on success
     */
    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable ID id) throws FunctionnalException;
}
