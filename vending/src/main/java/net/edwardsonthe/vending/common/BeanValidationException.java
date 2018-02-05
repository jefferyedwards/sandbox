package net.edwardsonthe.vending.common;

import java.util.Set;

public final class BeanValidationException extends VendingException {

  private static final long serialVersionUID = 1L;

  private final Class<?> clazz;

  private final Set<String> violations;

  public BeanValidationException(Class<?> clazz, Set<String> violations) {
    this.clazz = clazz;
    this.violations = violations;
  }

  public Class<?> getClazz() {
    return clazz;
  }

  @Override
  public String getMessage() {
    StringBuilder sb = new StringBuilder(clazz.getSimpleName() + " invalid");
    for (String violation : violations)
      sb.append("\n   " + violation);
    return sb.toString();
  }

  public Set<String> getViolations() {
    return violations;
  }

}
