package fr.diginamic.templatespringcrudapi.template.repository;

import fr.diginamic.templatespringcrudapi.template.entity.IIdentifiable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IEntityRepository<T extends IIdentifiable<ID>, ID> extends JpaRepository<T, ID>
{
}
