package net.edwardsonthe.security;

import org.springframework.security.access.expression.DenyAllPermissionEvaluator;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

@EnableGlobalMethodSecurity
public class CustomMethodSecurityConfiguration extends GlobalMethodSecurityConfiguration {

  @Override
  protected MethodSecurityExpressionHandler createExpressionHandler() {
    CustomMethodSecurityExpressionHandler expressionHandler = new CustomMethodSecurityExpressionHandler();
    expressionHandler.setPermissionEvaluator(new DenyAllPermissionEvaluator());
    return expressionHandler;
  }

}
