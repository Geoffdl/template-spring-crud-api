package fr.diginamic.templatespringcrudapi.template.service;



import fr.diginamic.templatespringcrudapi.template.dao.IDao;
import fr.diginamic.templatespringcrudapi.template.entity.IIdentifiable;
import fr.diginamic.templatespringcrudapi.template.mapper.IMapper;
import fr.diginamic.templatespringcrudapi.template.validation.IValidator;
import fr.diginamic.templatespringcrudapi.template.validation.ValidationError;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.parser.Entity;
import java.util.List;

/**
 * Abstract class for CRUD operations on a JPA entity. Contains generic default methods reading to use for child classes
 * @param <T> JPA entity
 * @param <ID> JPA entity's id type
 * @param <DTO> JPA entity DTO mapping
 */
public class AbstractCrudService<T extends IIdentifiable<ID>, ID, DTO> implements IService<T, ID, DTO>
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
    
    @Override @Transactional
    public DTO insert(T entity)
    {
        List<ValidationError> errors = validator.verifier(entity);
        if (!errors.isEmpty())
        {
            throw new RuntimeException(errors.getFirst().message());
        }
        if(dao.findById(entity.getId()) != null){
            throw new RuntimeException(entity.getClass().getSimpleName() + " already exists with this id");
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
    
    @Override @Transactional
    public DTO update(T entity)
    {
        List<ValidationError> errors = validator.verifier(entity);
        if (!errors.isEmpty())
        {
            throw new RuntimeException(errors.getFirst().message());
        }
        if(dao.findById(entity.getId()) == null){
            throw new RuntimeException(entity.getClass().getSimpleName() + " doesn't exists with this id");
        }
        T updatedEntity = dao.save(entity);
        return mapper.toDto(updatedEntity);
    }
    
    @Override @Transactional
    public void delete(ID id)
    {
        if (dao.findById(id) == null)
        {
            throw new RuntimeException("Entity not found with id: " + id);
        }
        dao.delete(id);
    }
}
