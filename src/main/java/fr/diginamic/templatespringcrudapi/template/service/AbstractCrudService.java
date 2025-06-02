package fr.diginamic.templatespringcrudapi.template.service;



import fr.diginamic.templatespringcrudapi.template.dao.IDao;
import fr.diginamic.templatespringcrudapi.template.mapper.IMapper;
import fr.diginamic.templatespringcrudapi.template.validation.IValidator;
import fr.diginamic.templatespringcrudapi.template.validation.ValidationError;

import java.util.List;

public class AbstractCrudService<T, ID, DTO> implements IService<T, ID, DTO>
{
    protected final IValidator<T> validator;
    protected final IMapper<T, DTO> mapper;
    protected final IDao<T, ID> dao;
    
    protected AbstractCrudService(IValidator<T> validator, IMapper<T, DTO> iMapper, IDao<T, ID> dao)
    {
        this.validator = validator;
        this.mapper = iMapper;
        this.dao = dao;
    }
    
    @Override public List<DTO> findAll()
    {
        {
            return List.of();
        }
    }
    
    @Override public DTO findOne()
    {
        return null;
    }
    
    @Override public DTO insert(T entity)
    {
        List<ValidationError> errors = validator.verifier(entity);
        if (!errors.isEmpty())
        {
            throw new RuntimeException(errors.getFirst().message());
        }
        return null;
    }
    
    @Override public DTO findById(ID id)
    {
        return null;
    }
    
    @Override public DTO update(T entity)
    {
        List<ValidationError> errors = validator.verifier(entity);
        if (!errors.isEmpty())
        {
            throw new RuntimeException(errors.getFirst().message());
        }
        return null;
    }
    
    @Override public void delete(ID id)
    {
    
    }
}
