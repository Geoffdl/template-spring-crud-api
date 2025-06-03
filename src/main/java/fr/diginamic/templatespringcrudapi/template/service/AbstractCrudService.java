package fr.diginamic.templatespringcrudapi.template.service;

import fr.diginamic.templatespringcrudapi.template.entity.IIdentifiable;
import fr.diginamic.templatespringcrudapi.template.exception.FunctionnalException;
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
    /**
     * validator implementation
     */
    protected final IValidator<T> validator;
    /**
     * mapper implementation
     */
    protected final IMapper<T, DTO> mapper;
    
    /**
     * repository implementation
     */
    protected final IEntityRepository<T, ID> repository;
    
    /**
     * constructor for child classes to @Autowired
     * @param validator  validator implementation
     * @param iMapper    mapper implementation
     * @param repository implementation
     */
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
    public DTO insert(T entity) throws FunctionnalException
    {
        ValidationHelper.validateEntity(entity, validator);
        
        if (repository.existsById(entity.getId()))
        {
            throw new FunctionnalException(
                  String.format("%s already exists with id: %s", entity.getClass().getSimpleName(), entity.getId())
            );
        }
        
        T savedEntity = repository.save(entity);
        return mapper.toDto(savedEntity);
    }
    
    @Override
    public DTO findById(ID id) throws FunctionnalException
    {
        T entity = repository.findById(id)
                             .orElseThrow(() -> new FunctionnalException("Entity not found with id: " + id));
        return mapper.toDto(entity);
    }
    
    @Override
    @Transactional
    public DTO update(T entity) throws FunctionnalException
    {
        ValidationHelper.validateEntity(entity, validator);
        
        if (!repository.existsById(entity.getId()))
        {
            throw new FunctionnalException(entity.getClass().getSimpleName() + " doesn't exists with this id");
        }
        T updatedEntity = repository.save(entity);
        return mapper.toDto(updatedEntity);
    }
    
    @Override
    @Transactional
    public void delete(ID id) throws FunctionnalException
    {
        if (!repository.existsById(id))
        {
            throw new FunctionnalException("Entity not found with id: " + id);
        }
        repository.deleteById(id);
    }
}
