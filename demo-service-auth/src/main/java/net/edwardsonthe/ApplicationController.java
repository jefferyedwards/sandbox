package net.edwardsonthe;

import java.security.Principal;
import java.util.Collections;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ApplicationController {

  @PreAuthorize("hasRole('CONFIDENTIAL')")
  @GetMapping("/confidential")
  public ModelAndView confidential(Principal principal) {
    return new ModelAndView("confidential", Collections.singletonMap("principal", principal));
  }

  @PreAuthorize("hasRole('USER')")
  @GetMapping("/protected")
  public ModelAndView protectedPage(Principal principal) {
    return new ModelAndView("demo-service-auth", Collections.singletonMap("principal", principal));
  }

  @GetMapping("/account")
  public String redirectToAccountPage(@AuthenticationPrincipal OAuth2AuthenticationToken authToken) {
    if (null == authToken) return "redirect:/";
    OidcUser user = (OidcUser) authToken.getPrincipal();
    return "redirect:" + user.getIssuer() + "/account?referrer=" + user.getIdToken().getAuthorizedParty();
  }

  @PreAuthorize("hasRole('SECRET')")
  @GetMapping("/secret")
  public ModelAndView secret(Principal principal) {
    return new ModelAndView("secret", Collections.singletonMap("principal", principal));
  }

  @PreAuthorize("hasRole('TOP-SECRET')")
  @GetMapping("/top-secret")
  public ModelAndView topSecret(Principal principal) {
    return new ModelAndView("top-secret", Collections.singletonMap("principal", principal));
  }

  @GetMapping("/")
  public String unprotectedPage(Model model, Principal principal) {
    model.addAttribute("principal", principal);
    return "index";
  }

}
