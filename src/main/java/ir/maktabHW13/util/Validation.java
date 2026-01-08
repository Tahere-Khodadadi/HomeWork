package ir.maktabHW13.util;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.Set;

public class Validation {

     private static final ValidatorFactory factory = jakarta.validation.Validation.buildDefaultValidatorFactory();
        private static final Validator validator = factory.getValidator();

        public static <T> void validators(T entity) {
            if (validator == null) {
                throw new RuntimeException("Validator could not be initialized. Check your dependencies!");
            }

            Set<ConstraintViolation<T>> violations = validator.validate(entity);

            if (!violations.isEmpty()) {
                StringBuilder sb = new StringBuilder();
                for (ConstraintViolation<T> violation : violations) {
                    sb.append(violation.getMessage()).append("\n");
                }
                throw new RuntimeException("Validation Error: \n" + sb.toString());
            }
        }

}
