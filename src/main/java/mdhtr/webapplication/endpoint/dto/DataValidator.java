package mdhtr.webapplication.endpoint.dto;

import com.fasterxml.jackson.databind.util.StdConverter;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Set;

abstract class DataValidator<T> extends StdConverter<T, T> {
    @Override
    public T convert(T t) {
        try (ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory()) {
            Validator validator = validatorFactory.getValidator();
            Set<ConstraintViolation<T>> violations = validator.validate(t);
            if (!violations.isEmpty()) {
                throw new ConstraintViolationException(violations);
            }
        }
        return t;
    }
}
