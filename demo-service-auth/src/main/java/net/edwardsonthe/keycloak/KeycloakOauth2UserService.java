package net.edwardsonthe.keycloak;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.util.CollectionUtils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class KeycloakOauth2UserService extends OidcUserService {

  private final OAuth2Error INVALID_REQUEST = new OAuth2Error(OAuth2ErrorCodes.INVALID_REQUEST);

  private final GrantedAuthoritiesMapper authoritiesMapper;

  private final JwtDecoder jwtDecoder;

  @Override
  public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {

    OidcUser user = super.loadUser(userRequest);

    Collection<? extends GrantedAuthority> keycloakAuthorities = extractKeycloakAuthorities(userRequest);

    Set<GrantedAuthority> authorities = new LinkedHashSet<>();
    authorities.addAll(user.getAuthorities());
    authorities.addAll(keycloakAuthorities);

    return new DefaultOidcUser(authorities, userRequest.getIdToken(), user.getUserInfo(), "preferred_username");

  }

  @SuppressWarnings("unchecked")
  Collection<? extends GrantedAuthority> extractKeycloakAuthorities(OidcUserRequest userRequest) {

    Jwt token = parseJwt(userRequest.getAccessToken().getTokenValue());

    Map<String, Object> resourceMap = (Map<String, Object>) token.getClaims().get("resource_access");
    String clientId = userRequest.getClientRegistration().getClientId();

    Map<String, Map<String, Object>> clientResource = (Map<String, Map<String, Object>>) resourceMap.get(clientId);
    if (CollectionUtils.isEmpty(clientResource)) { return Collections.emptyList(); }

    List<String> clientRoles = (List<String>) clientResource.get("roles");
    if (CollectionUtils.isEmpty(clientRoles)) { return Collections.emptyList(); }

    Collection<? extends GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(clientRoles.toArray(new String[0]));
    if (authoritiesMapper == null) { return authorities; }

    return authoritiesMapper.mapAuthorities(authorities);

  }

  Jwt parseJwt(String accessTokenValue) {
    try {
      return jwtDecoder.decode(accessTokenValue);
    } catch (JwtException e) {
      throw new OAuth2AuthenticationException(INVALID_REQUEST, e);
    }
  }

}
