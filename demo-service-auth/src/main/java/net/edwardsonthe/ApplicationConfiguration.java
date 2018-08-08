package net.edwardsonthe;

import static org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestRedirectFilter.DEFAULT_AUTHORIZATION_REQUEST_BASE_URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoderJwkSupport;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import net.edwardsonthe.keycloak.KeycloakLogoutHandler;
import net.edwardsonthe.keycloak.KeycloakOauth2UserService;
import net.edwardsonthe.keycloak.KeycloakProperties;
import net.edwardsonthe.security.CustomInterceptor;
import net.edwardsonthe.security.CustomMethodSecurityConfiguration;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationConfiguration {

  @Autowired
  KeycloakProperties keycloakProperties;

  @Bean
  CustomMethodSecurityConfiguration customMethodSecurityConfiguration() {
    return new CustomMethodSecurityConfiguration();
  }

  @Bean
  LogoutHandler logoutHandler() {
    return new KeycloakLogoutHandler(new RestTemplate());
  }

  @Bean
  OidcUserService oidcUserService(OAuth2ClientProperties oauth2ClientProperties) {
    NimbusJwtDecoderJwkSupport jwtDecoder = new NimbusJwtDecoderJwkSupport(
        oauth2ClientProperties.getProvider().get("keycloak").getJwkSetUri());
    SimpleAuthorityMapper authoritiesMapper = new SimpleAuthorityMapper();
    authoritiesMapper.setConvertToUpperCase(true);
    return new KeycloakOauth2UserService(authoritiesMapper, jwtDecoder);
  }

  @Bean
  WebMvcConfigurer webMvcConfigurer() {
    return new WebMvcConfigurer() {
      @Override
      public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new CustomInterceptor());
      }
    };
  }

  @Bean
  WebSecurityConfigurerAdapter webSecurityConfigurerAdapter(OidcUserService oidcUserService, LogoutHandler logoutHandler) {

    return new WebSecurityConfigurerAdapter() {

      @Override
      public void configure(HttpSecurity http) throws Exception {
        http.sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
            .and()
            .authorizeRequests()
            .anyRequest()
            .permitAll()
            .and()
            .logout()
            .addLogoutHandler(logoutHandler)
            .and()
            .oauth2Login()
            .userInfoEndpoint()
            .oidcUserService(oidcUserService)
            .and()
            .loginPage(DEFAULT_AUTHORIZATION_REQUEST_BASE_URI + "/" + keycloakProperties.getRealmName());
      }

    };

  }

}
