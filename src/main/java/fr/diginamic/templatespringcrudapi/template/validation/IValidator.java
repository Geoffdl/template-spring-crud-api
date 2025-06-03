package fr.diginamic.templatespringcrudapi.template.validation;

import java.util.List;

/**
 * Validator interface for business rules verification.
 * The implementation need to initialise an arraylist of ValidationErrors and handle each business rules
 * @param <T> JPA entity
 */
public interface IValidator<T>
{
    /**
     * Checks that the attributes of the entity match the business rules and returns a list of errors
     * @param entity JPA entity
     * @return list of errors
     */
    List<ValidationError> verifier(T entity);
}
