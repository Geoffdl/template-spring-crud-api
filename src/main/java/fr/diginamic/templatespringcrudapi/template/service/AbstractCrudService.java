package fr.diginamic.templatespringcrudapi.template.service;

import fr.diginamic.templatespringcrudapi.template.entity.IIdentifiable;
import fr.diginamic.templatespringcrudapi.template.exception.FunctionnalException;
import fr.diginamic.templatespringcrudapi.template.exception.TechnicalException;
import fr.diginamic.templatespringcrudapi.template.mapper.IMapper;
import fr.diginamic.templatespringcrudapi.template.repository.IEntityRepository;
import fr.diginamic.templatespringcrudapi.template.utils.ValidationHelper;
import fr.diginamic.templatespringcrudapi.template.validation.IValidator;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Abstract class for CRUD operations on a JPA entity. Contains generic default methods reading to use for child classes
 * @param <T>   JPA entity
 * @param <ID>  JPA entity's id type
 * @param <DTO> JPA entity DTO mapping
 */
public class AbstractCrudService<T extends IIdentifiable<ID>, ID, DTO> implements ICrudService<T, ID, DTO>
{
    
    protected final IValidator<T> validator;
    protected final IMapper<T, DTO> mapper;
    protected final IEntityRepository<T, ID> repository;
    
    protected AbstractCrudService(IValidator<T> validator, IMapper<T, DTO> iMapper, IEntityRepository<T, ID> repository)
    {
        this.validator = validator;
        this.mapper = iMapper;
        this.repository = repository;
    }
    
    @Override
    public List<DTO> findAll()
    {
        return repository.findAll().stream()
                         .map(mapper::toDto)
                         .toList();
    }
    
    @Override
    @Transactional
    public DTO insert(T entity) throws FunctionnalException, TechnicalException
    {
        if (entity == null)
        {
            throw new TechnicalException("Cannot insert null entity");
        }
        
        ValidationHelper.validateEntity(entity, validator);
        
        if (entity.getId() != null && repository.existsById(entity.getId()))
        {
            throw new FunctionnalException(
                  String.format("%s already exists with id: %s", entity.getClass().getSimpleName(), entity.getId())
            );
        }
        
        T savedEntity = repository.save(entity);
        return mapper.toDto(savedEntity);
    }
    
    @Override
    public DTO findById(ID id) throws FunctionnalException, TechnicalException
    {
        if (id == null)
        {
            throw new TechnicalException("ID cannot be null when finding an entity");
        }
        
        T entity = repository.findById(id)
                             .orElseThrow(() -> new FunctionnalException("Entity not found with id: " + id));
        return mapper.toDto(entity);
    }
    
    @Override
    @Transactional
    public DTO update(T entity) throws FunctionnalException, TechnicalException
    {
        if (entity == null)
        {
            throw new TechnicalException("Cannot update null entity");
        }
        
        ValidationHelper.validateEntity(entity, validator);
        
        if (entity.getId() == null || !repository.existsById(entity.getId()))
        {
            throw new FunctionnalException(entity.getClass().getSimpleName() + " doesn't exist with this id");
        }
        
        T updatedEntity = repository.save(entity);
        return mapper.toDto(updatedEntity);
    }
    
    @Override
    @Transactional
    public void delete(ID id) throws FunctionnalException
    {
        if (id == null || !repository.existsById(id))
        {
            throw new FunctionnalException("Entity not found with id: " + id);
        }
        
        repository.deleteById(id);
    }
}
