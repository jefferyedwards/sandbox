package net.edwardsonthe.keycloak;

import java.net.URL;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "keycloak", ignoreUnknownFields = true)
@Data
public class KeycloakProperties {

  private URL baseUrl;

  private String realmName;

  private URL realmUrl;

}
