package net.edwardsonthe.security;

import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
public class CustomSecurityExpressionRoot extends SecurityExpressionRoot implements MethodSecurityExpressionOperations {

  private Object filterObject;

  private Object returnObject;

  public CustomSecurityExpressionRoot(Authentication authentication) {
    super(authentication);
  }

  @Override
  public Object getThis() {
    return this;
  }

  public boolean isPermitted(String resource) {
    boolean permitted = true;
    log.info("isPermitted('{}')? {}", resource, permitted);
    return permitted;
  }

}
