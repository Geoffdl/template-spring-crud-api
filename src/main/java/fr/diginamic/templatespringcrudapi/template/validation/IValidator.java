package fr.diginamic.templatespringcrudapi.template.validation;

import java.util.List;

public interface IValidator<T>
{
    List<ValidationError> verifier(T entity);
}
