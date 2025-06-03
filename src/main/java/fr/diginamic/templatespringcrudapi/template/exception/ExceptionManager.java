package fr.diginamic.templatespringcrudapi.template.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionManager
{
    @ExceptionHandler(FunctionnalException.class)
    public ResponseEntity<String> traiterErreur(FunctionnalException e)
    {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
    
    @ExceptionHandler(TechnicalException.class)
    public ResponseEntity<String> traiterErreur(TechnicalException e)
    {
        return ResponseEntity.internalServerError().body(e.getMessage());
    }
}
