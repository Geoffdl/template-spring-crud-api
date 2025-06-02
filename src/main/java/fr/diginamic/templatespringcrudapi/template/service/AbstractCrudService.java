package fr.diginamic.templatespringcrudapi.template.service;



import fr.diginamic.templatespringcrudapi.template.dao.IDao;
import fr.diginamic.templatespringcrudapi.template.mapper.IMapper;
import fr.diginamic.templatespringcrudapi.template.validation.IValidator;
import fr.diginamic.templatespringcrudapi.template.validation.ValidationError;

import java.util.List;

/**
 * Abstract class for CRUD operations on a JPA entity. Contains generic default methods reading to use for child classes
 * @param <T> JPA entity
 * @param <ID> JPA entity's id type
 * @param <DTO> JPA entity DTO mapping
 */
public class AbstractCrudService<T, ID, DTO> implements IService<T, ID, DTO>
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
     * dao implementation
     */
    protected final IDao<T, ID> dao;
    
    /**
     * constructor for child classes to @Autowired
     * @param validator validator implementation
     * @param iMapper mapper implementation
     * @param dao dao implementation
     */
    protected AbstractCrudService(IValidator<T> validator, IMapper<T, DTO> iMapper, IDao<T, ID> dao)
    {
        this.validator = validator;
        this.mapper = iMapper;
        this.dao = dao;
    }
    
    @Override
    public List<DTO> findAll()
    {
        return dao.findAll().stream()
                  .map(mapper::toDto)
                  .toList();
    }
    
    @Override
    public DTO insert(T entity)
    {
        List<ValidationError> errors = validator.verifier(entity);
        if (!errors.isEmpty())
        {
            throw new RuntimeException(errors.getFirst().message());
        }
        
        T savedEntity = dao.save(entity);
        return mapper.toDto(savedEntity);
    }
    
    @Override
    public DTO findById(ID id)
    {
        T entity = dao.findById(id);
        if(entity == null){
            throw new RuntimeException("Entity not found with id: " + id);
        }
        return mapper.toDto(entity);
    }
    
    @Override
    public DTO update(T entity)
    {
        List<ValidationError> errors = validator.verifier(entity);
        if (!errors.isEmpty())
        {
            throw new RuntimeException(errors.getFirst().message());
        }
        
        T updatedEntity = dao.save(entity);
        return mapper.toDto(updatedEntity);
    }
    
    @Override
    public void delete(ID id)
    {
        if (dao.findById(id) == null)
        {
            throw new RuntimeException("Entity not found with id: " + id);
        }
        dao.delete(id);
    }
}
