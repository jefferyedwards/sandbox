package net.edwardsonthe.vending;

import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.shell.jline.PromptProvider;

/**
 * The Spring Boot Vending Machine application.
 * 
 * @author jeff
 */
@SpringBootApplication
public class Application {

  static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

  public static void main(String[] args) throws Exception {
    SpringApplication.run(Application.class, args);
  }

  @Bean
  public PromptProvider myPromptProvider() {
    return () -> new AttributedString("vending:> ", AttributedStyle.DEFAULT.foreground(AttributedStyle.YELLOW));
  }

}
