package fr.diginamic.templatespringcrudapi.template.utils;

import fr.diginamic.templatespringcrudapi.template.exception.FunctionnalException;
import fr.diginamic.templatespringcrudapi.template.validation.IValidator;
import fr.diginamic.templatespringcrudapi.template.validation.ValidationError;

import java.util.List;

public class ValidationHelper
{
    public static <T> void validateEntity(T entity, IValidator<T> validator) throws FunctionnalException
    {
        List<ValidationError> errors = validator.verifier(entity);
        if (!errors.isEmpty())
        {
            throw new FunctionnalException(errors.getFirst().message());
        }
    }
}
