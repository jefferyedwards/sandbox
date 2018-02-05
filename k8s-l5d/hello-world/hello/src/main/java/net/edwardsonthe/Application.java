package net.edwardsonthe;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

}

@RestController
class Controller {

  final Logger LOGGER = LoggerFactory.getLogger(Controller.class);

  @Value("${world.uri}")
  private String worldUri;
  
  @Autowired
  RestTemplateBuilder builder;

  private RestTemplate restTemplate;

  @RequestMapping(value = "/hello", method = RequestMethod.GET, produces = "text/plain")
  public String hello(@RequestParam(name = "source", defaultValue = "World") String source) {
    LOGGER.info("~~~ source = {}", source);
    LOGGER.info("~~~    uri = {}", worldUri + "?source=" + source);
    String name = restTemplate.getForObject(worldUri + "?source=" + source, String.class);
    LOGGER.info("Hello {}", name);
    return "Hello" + name;
  }

  @PostConstruct
  void postConstruct() {
    LOGGER.info("~~~~~ worldURI = {}", worldUri);
    restTemplate = builder.build();
  }

}
