package fr.diginamic.templatespringcrudapi.template.mapper;

public interface IMapper<T, DTO>
{
    DTO toDTO(T entity);
    
    T toEntity(DTO dto);
}
