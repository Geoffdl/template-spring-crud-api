package fr.diginamic.templatespringcrudapi.template.mapper;

/**
 * Mapper interface for JPA entity // DTO conversion
 * @param <T> JPA Entity
 * @param <DTO> DTO mapping
 */
public interface IMapper<T, DTO>
{
    /**
     * Entity to DTO
     * @param entity entity
     * @return dto
     */
    DTO toDto(T entity);
    
    /**
     * DTO to Entity
     * @param dto dto
     * @return entity
     */
    T toEntity(DTO dto);
}
