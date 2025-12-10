package com.academia.fit.service.error;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.experimental.UtilityClass;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Set;

@UtilityClass
public class ErrorMessageService {

    public String formatValidationErrors(MethodArgumentNotValidException ex) {
        StringBuilder errors = new StringBuilder("Erro de validação nos campos enviados: ");
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errors.append("[").append(fieldError.getField())
                    .append(": ").append(fieldError.getDefaultMessage()).append("] ");
        }
        return errors.toString().trim();
    }

    public String formatConstraintViolations(ConstraintViolationException ex) {
        StringBuilder errors = new StringBuilder("Erro de validação: ");
        Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
        for (ConstraintViolation<?> violation : violations) {
            errors.append("[").append(violation.getPropertyPath())
                    .append(": ").append(violation.getMessage()).append("] ");
        }
        return errors.toString().trim();
    }
}
