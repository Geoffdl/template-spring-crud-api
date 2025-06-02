package fr.diginamic.templatespringcrudapi.template.controller;

import fr.diginamic.templatespringcrudapi.template.service.IService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public abstract class AbstractController<T, ID, DTO>
{
    public abstract IService<T, ID, DTO> getService();
    
    public ResponseEntity<List<DTO>> findAll()
    {
        return ResponseEntity.ok(getService().findAll());
    }
    
    @GetMapping({"/{id}"})
    public ResponseEntity<DTO> findOne(@PathVariable ID id)
    {
        return ResponseEntity.ok(getService().findById(id));
    }
    
    @PostMapping
    public ResponseEntity<DTO> insert(@RequestBody T entity)
    {
        return ResponseEntity.ok(getService().insert(entity));
    }
    
    @PutMapping
    public ResponseEntity<DTO> update(@RequestBody T entity)
    {
        return ResponseEntity.ok(getService().update(entity));
    }
    
    @DeleteMapping
    public void delete(ID id)
    {
        getService().delete(id);
    }
}
