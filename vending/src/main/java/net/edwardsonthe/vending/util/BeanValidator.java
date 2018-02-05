package net.edwardsonthe.vending.util;

import java.util.Set;
import java.util.TreeSet;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.edwardsonthe.vending.common.BeanValidationException;

@Component
public class BeanValidator {

  static final String VIOLATION = "ConstraintViolation: %s attribute '%s' %s";

  @Autowired
  private Validator validator;

  public <T> void validate(T object) throws BeanValidationException {

    Set<ConstraintViolation<T>> violations_ = validator.validate(object);
    if (violations_.isEmpty()) return;

    Set<String> violations = new TreeSet<>();
    violations_.forEach(v -> violations.add(String.format(VIOLATION, v.getRootBeanClass().getSimpleName(), v.getPropertyPath(), v.getMessage())));
    throw new BeanValidationException(object.getClass(), violations);

  }

}
